package com.example.memorizing.controller

import com.example.memorizing.entity.Card
import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.ECardType
import com.example.memorizing.entity.ELanguage
import com.example.memorizing.repository.UserRepository
import com.example.memorizing.service.CardService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Controller

@Controller
class LocalController(
    private val userRepository: UserRepository,
    private val cardService: CardService,
) {
    private val logger: Logger = LogManager.getLogger(LocalController::class.java)
    private var random = true

    @EventListener(ApplicationReadyEvent::class)
    fun startLoopAfterStarting() {
        logger.info("START")

        val username = "Sergey"
        val user = userRepository.getUserByUsername(username)
        val listOfSetOfCards = cardService.getSetOfCardsByUsername(user.username)
        val setOfPairLanguage: Set<Pair<ELanguage, ELanguage>> = listOfSetOfCards.map { it.pair }.toSet()

        while (true) {
            val pairOfLanguage: Pair<ELanguage, ELanguage> = choosePairOfLanguage(setOfPairLanguage) ?: break
            if (pairOfLanguage.second != user.nativeLanguage) throw Exception("wrong pair of languages in setOfPairLanguage")

            val cardType: ECardType = chooseCardTypeOrSetLanguageOrContinue() ?: continue
            val setOfCards = listOfSetOfCards.find { it.pair == pairOfLanguage } ?: throw Exception("not found")

            val mapOfCards = setOfCards.mapOfCards.filter { it.value.type == cardType }.toMutableMap()
            showStatistic(mapOfCards, cardType.typeName)

            val chooseMode = chooseMode(pairOfLanguage, cardType) ?: continue

            when {
                chooseMode.first != null && chooseMode.second != null -> startTestLoop(
                    mapOfCards,
                    chooseMode.first!!,
                    chooseMode.second!!,
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
        cardStatus: ECardStatus,
        setOfCardsId: Int,
        userMaxPoint: Int
    ) {
        var countMistake = 0
        var savedCards = 0
        var completedCards = 0

        val listOfPairs = mapOfCards.map { (key, value) ->
            if (translateToNative) key to value.translate else value.translate to key
        }

        listOfPairs.forEach {
            println("${it.first}:")
            val input = readln()
            if (input == "0") return

            val card =
                if (translateToNative) mapOfCards[it.first] else mapOfCards[it.second] ?: throw Exception("not found")

            val isCorrectUserValue = cardService.checkCard(card!!, setOfCardsId, input, translateToNative, userMaxPoint)


        }

        val keys = mapOfCards.keys

        // Сделать через HashMap (какой язык такой и ключ)
        //
        // Поиск и изменение сделать зависимым от типа языка

        keys.forEach {
            print("${it}:")

            val input = readln()
            if (input == "0") return

            val card = getCardsByLanguageType(languageType)[it]

            if (card!!.translate.contains(input)) {
                // correct answer
                if (card.status == ECardStatus.HARD) {
                    card.status = ECardStatus.NORMAL
                    card.pointFromNative = 0
                    savedCards++
                }
                if (card.pointFromNative >= maxPoint) {
                    card.pointFromNative = card.pointFromNative + 1
                    card.status = ECardStatus.COMPLETED
                    println("You learn this ${card.type.name}!")
                    completedCards++
                } else {
                    card.pointFromNative = card.pointFromNative + 1
                    print("${card.translate}:${card.pointFromNative}")
                    println()
                }
                fileService.saveCards()
            } else {
                // wrong answer
                if (card.status == ECardStatus.HARD) {
                    card.pointFromNative = card.pointFromNative - 1
                } else card.pointFromNative = -1

                card.status = ECardStatus.HARD
                fileService.saveCards()
                countMistake++
                println("Opss! Wrong!")
                println("${card.value}:${card.translate}:${card.pointFromNative}")
            }
        }

        println("Common count:          ${keys.size}")
        println("Amount of mistakes:    $countMistake")
        println("Amount of saved:       $savedCards")
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
                mapOfCards.forEach { if (it.value.status == ECardStatus.HARD) keys.add(it.key) }
            }
            ECardStatus.NORMAL -> {
                mapOfCards.forEach {
                    if (it.value.status == ECardStatus.HARD || it.value.status == ECardStatus.NORMAL) keys.add(it.key)
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

        println("Statistics:7 - show ${cardType.typeName}s sorted by count")

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
        println("Choose languages?")
        setOfPairLanguage.forEachIndexed { index, pair -> println("${index + 1} - ${pair.first}_${pair.second}; ") }
        print("0 - exit;")

        do {
            return when (val input = readLine()?.toInt()) {
                0 -> null
                in setOfPairLanguage.indices + 1 -> setOfPairLanguage.elementAt(input!!.minus(1))
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
        } while (true)

    }

    private fun chooseCardTypeOrSetLanguageOrContinue(): ECardType? {
        println("What do you want to learn?")
        ECardType.values().forEachIndexed { index, cardType -> println("${index + 1} - ${cardType.typeName}; ") }
        print("0 - exit;")

        do {
            return when (val input = readLine()?.toInt()) {
                0 -> null
                in ECardType.values().indices + 1 -> ECardType.values().elementAt(input!!.minus(1))
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
        } while (true)
    }

    private fun showStatistic(mapOfCards: MutableMap<String, Card>, cardTypeName: String) {
        println("--- Statistics ---")
        println("${cardTypeName}s: ${mapOfCards.size}")
        var countCompleted = 0
        mapOfCards.forEach { if (it.value.status == ECardStatus.COMPLETED) countCompleted++ }
        println("Completed ${cardTypeName}s: $countCompleted ")

        var countHard = 0
        mapOfCards.forEach { if (it.value.status == ECardStatus.HARD) countHard++ }
        println("Hard ${cardTypeName}s: $countHard ")
    }

    private fun showObjectsStatistic(mapOfCards: MutableMap<String, Card>, cardType: ECardType) {
        mapOfCards.values.sortedByDescending { it.pointFromNative }.forEach {
            if (it.type == cardType) println("${it.value}:${it.translate}:${it.pointFromNative}:${it.status}")
        }
    }
}