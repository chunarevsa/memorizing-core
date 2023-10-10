package com.example.memorizing.controller

import com.example.memorizing.entity.*
import com.example.memorizing.repository.UserRepository
import com.example.memorizing.service.CardService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class LocalController(
    private val userRepository: UserRepository,
    private val cardService: CardService,
) {
    private val logger: Logger = LogManager.getLogger(LocalController::class.java)
    private var random = true

    @EventListener(ApplicationReadyEvent::class)
    fun startLoopAfterStarting() {
        val user: User = userRepository.findUserByUsername("Sergei") ?: run {
            userRepository.addUser(User("Sergei", ELanguage.RUS))
            userRepository.findUserByUsername("Sergei")!!
        }

        val rootOfSets: RootOfSets = if (user.rootOfSetIds.isNotEmpty()) {
            cardService.getRootOfSets(user.rootOfSetIds.first())!!
        } else {
            val rootOfSetsId = cardService.createRootOfSet(user.username!!)
            user.rootOfSetIds.add(rootOfSetsId)
            userRepository.saveUser(user)
            cardService.getRootOfSets(rootOfSetsId)!!
        }

        if (rootOfSets.setOfCardsIds.isEmpty()) {
            rootOfSets.setOfCardsIds.add(cardService.createSetOfCards(ELanguage.ENG, user.nativeLanguage!!))
            rootOfSets.setOfCardsIds.add(cardService.createSetOfCards(ELanguage.DEU, user.nativeLanguage))
            cardService.saveRootOfSets(rootOfSets)
        }

        val listOfSetOfCards: List<SetOfCards> = cardService.getListOfSetOfCards(user.rootOfSetIds)
        val setOfPairLanguage: Set<Pair<ELanguage, ELanguage>> = listOfSetOfCards.map { it.pair!! }.toSet()

        logger.info("START")
        while (true) {
            val pairOfLanguage: Pair<ELanguage, ELanguage> = choosePairOfLanguage(setOfPairLanguage) ?: break
            if (pairOfLanguage.second != user.nativeLanguage) throw Exception("wrong pair of languages in setOfPairLanguage")

            val cardType: ECardType = chooseCardTypeOrSetLanguageOrContinue() ?: continue
            val setOfCards = listOfSetOfCards.find { it.pair == pairOfLanguage } ?: throw Exception("not found")

            val mapOfCards = setOfCards.mapOfCards.filter { it.value.type == cardType }.toMutableMap()
            showStatistic(mapOfCards, cardType.name)

            val chooseMode = chooseMode(pairOfLanguage, cardType) ?: continue

            when {
                chooseMode.first != null && chooseMode.second != null -> startTestLoop(
                    mapOfCards,
                    chooseMode.first!!,
                    setOfCards.id,
                    user.maxPoint
                )
                chooseMode.first == null && chooseMode.second != null -> startStudyingLoop(
                    mapOfCards,
                    chooseMode.second!!
                )
                chooseMode.first == null && chooseMode.second == null -> showObjectsStatistic(mapOfCards, cardType)
            }
        }

        logger.error("END")
    }

    private fun startTestLoop(
        mapOfCards: MutableMap<String, Card>,
        translateToNative: Boolean,
        setOfCardsId: String,
        userMaxPoint: Int
    ) {
        var countMistake = 0
        var correctAnswer = 0
        var completedCards = 0

        val listOfPairs = mapOfCards.map { (key, value) ->
            if (translateToNative) key to value.translate else value.translate to key
        }

        listOfPairs.forEach {
            print("${it.first}:")
            val input = readln()
            if (input == "0") return

            val card =
                if (translateToNative) mapOfCards[it.first] else mapOfCards[it.second] ?: throw Exception("not found")

            val isCorrect = cardService.checkCard(card!!, setOfCardsId, input, translateToNative, userMaxPoint)
            if (isCorrect) {
                val point = if (translateToNative) card.pointToNative else card.pointFromNative

                if (point >= userMaxPoint - 1) {
                    println("You learn this ${card.type.name}!")
                    completedCards++
                }
                correctAnswer++

            } else {
                print("${it.first}:${it.second}")
                println()
                countMistake++
            }

        }

        println("Common count:          ${listOfPairs.size}")
        println("Amount of mistakes:    $countMistake")
        println("Amount of correct:     $correctAnswer")
        println("Amount of completed:   $completedCards")
    }

    private fun startStudyingLoop(mapOfCards: MutableMap<String, Card>, status: ECardStatus) {
        getKeysByStatusAndCardType(mapOfCards, status).forEach {
            print("${it}:${mapOfCards[it]!!.translate}")
            readln()
        }
    }

    private fun getKeysByStatusAndCardType(
        mapOfCards: MutableMap<String, Card>,
        status: ECardStatus
    ): MutableList<String> {
        val keys = mutableListOf<String>()

        // If choose NORMAL, we return HARD + NORMAL
        when (status) {
            ECardStatus.HARD -> {
                mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.HARD) keys.add(it.key) }
            }
            ECardStatus.NORMAL -> {
                mapOfCards.forEach {
                    if (it.value.statusFromNative == ECardStatus.HARD || it.value.statusFromNative == ECardStatus.NORMAL) keys.add(
                        it.key
                    )
                }
            }
            else -> {}
        }

        return if (random) keys.shuffled().toMutableList() else keys
    }

    private fun chooseMode(languages: Pair<ELanguage, ELanguage>, cardType: ECardType): Pair<Boolean?, ECardStatus?>? {
        println("TEST:      1 - all ${languages.second}/${languages.first};         3 - all ${languages.first}/${languages.second}  ")
        println("TEST:      2 - only hard ${languages.second}/${languages.first};   4 - only hard ${languages.first}/${languages.second};")

        println("Studying:  5 - all;                6 - only hard;")

        println("Statistics:7 - show ${cardType.name}s sorted by count")

        println("0 - exit")
        print("Which type do you select?:")

        // TODO: Второй всегда native
        do {
            return when (readLine()?.toInt()) {
                0 -> null
                1 -> Pair(false, ECardStatus.NORMAL)
                2 -> Pair(false, ECardStatus.HARD)
                3 -> Pair(true, ECardStatus.NORMAL)
                4 -> Pair(true, ECardStatus.HARD)
                5 -> Pair(null, ECardStatus.NORMAL)
                6 -> Pair(null, ECardStatus.HARD)
                7 -> Pair(null, null)
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
        } while (true)
    }

    private fun choosePairOfLanguage(setOfPairLanguage: Set<Pair<ELanguage, ELanguage>>): Pair<ELanguage, ELanguage>? {
        println("Choose languages")
        setOfPairLanguage.forEachIndexed { index, pair -> print("${index + 1} - ${pair.first}_${pair.second}; ") }
        println("0 - exit;")

        do {
            return when (val input = readLine()?.toInt()) {
                0 -> null
                in 1..setOfPairLanguage.size  -> setOfPairLanguage.elementAt(input!!.minus(1))
                else -> {
                    println("I don't understand you")
                    println("Choose languages")
                    setOfPairLanguage.forEachIndexed { index, pair -> print("${index + 1} - ${pair.first}_${pair.second}; ") }
                    println("0 - exit;")
                    continue
                }
            }
        } while (true)

    }

    private fun chooseCardTypeOrSetLanguageOrContinue(): ECardType? {
        println("What do you want to learn?")
        ECardType.values().forEachIndexed { index, cardType -> print("${index + 1} - ${cardType.name}; ") }
        println("0 - exit;")

        do {
            return when (val input = readLine()?.toInt()) {
                0 -> null
                in 1..ECardType.values().size + 1 -> ECardType.values().elementAt(input!!.minus(1))
                else -> {
                    logger.info("I don't understand you")
                    println("What do you want to learn?")
                    ECardType.values().forEachIndexed { index, cardType -> print("${index + 1} - ${cardType.name}; ") }
                    println("0 - exit;")
                    continue
                }
            }
        } while (true)
    }

    private fun showStatistic(mapOfCards: MutableMap<String, Card>, cardTypeName: String) {
        println("--- Statistics ---")
        println("${cardTypeName}s: ${mapOfCards.size}")
        var countCompleted = 0
        mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.COMPLETED) countCompleted++ }
        println("Completed ${cardTypeName}s: $countCompleted ")

        var countHard = 0
        mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.HARD) countHard++ }
        println("Hard ${cardTypeName}s: $countHard ")
    }

    private fun showObjectsStatistic(mapOfCards: MutableMap<String, Card>, cardType: ECardType) {
        mapOfCards.values.sortedByDescending { it.pointFromNative }.forEach {
            if (it.type == cardType) println("${it.value}:${it.translate}:${it.pointFromNative}:${it.statusFromNative}")
        }
    }
}