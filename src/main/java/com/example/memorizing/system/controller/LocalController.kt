package com.example.memorizing.system.controller

//import com.example.memorizing.card.Card
//import com.example.memorizing.card.EModeType
//import com.example.memorizing.storage.Storage
//import com.example.memorizing.cardStock.CardStock
//import com.example.memorizing.card.ECardStatus
//import com.example.memorizing.system.entity.*
//import com.example.memorizing.user.User
//import org.apache.logging.log4j.LogManager
//import org.apache.logging.log4j.Logger
//import org.springframework.boot.context.event.ApplicationReadyEvent
//import org.springframework.context.event.EventListener
//import org.springframework.stereotype.Service

//@Service
//class LocalController(
//    private val userRepository: UserRepository,
//    private val studyingService: StudyingService,
//) {
//    private val logger: Logger = LogManager.getLogger(LocalController::class.java)
//    private var random = true
//
//    @EventListener(ApplicationReadyEvent::class)
//    fun startLoopAfterStarting() {
//        val user: User = userRepository.findUserByUsername("Sergei") ?: run {
//            userRepository.addUser(User("Sergei", ELanguage.RUS))
//            userRepository.findUserByUsername("Sergei")!!
//        }
//
//        val storage: Storage = if (user.rootOfSetIds.isNotEmpty()) {
//            studyingService.getRootOfSets(user.rootOfSetIds.first())!!
//        } else {
//            val rootOfSetsId = studyingService.createRootOfSet(user.username!!)
//            user.rootOfSetIds.add(rootOfSetsId)
//            userRepository.saveUser(user)
//            studyingService.getRootOfSets(rootOfSetsId)!!
//        }
//
//        if (storage.setOfCardsIds.isEmpty()) {
//            storage.setOfCardsIds.add(studyingService.createSetOfCards(ELanguage.ENG, user.nativeLanguage!!, user.maxPoint))
//            storage.setOfCardsIds.add(studyingService.createSetOfCards(ELanguage.DEU, user.nativeLanguage, user.maxPoint))
//            studyingService.saveRootOfSets(storage)
//        }
//
//        val listOfCardStocks: List<CardStock> = studyingService.getListOfSetOfCards(user.rootOfSetIds)
//        val setOfPairLanguage: Set<Pair<ELanguage, ELanguage>> = listOfCardStocks.map { it.pair!! }.toSet()
//
//        logger.info("START")
//        while (true) {
//            println()
//            val pairOfLanguage: Pair<ELanguage, ELanguage> = choosePairOfLanguage(setOfPairLanguage) ?: break
//            if (pairOfLanguage.second != user.nativeLanguage) throw Exception("wrong pair of languages in setOfPairLanguage")
//
//            val cardType: ECardType = chooseCardTypeOrSetLanguageOrContinue() ?: continue
//            val setOfCards = listOfCardStocks.find { it.pair == pairOfLanguage } ?: throw Exception("not found")
//
//            showShortStatisticByCardType(setOfCards.cards, cardType)
//
//            val chosenMode = chooseMode(pairOfLanguage, cardType) ?: continue
//            val mapOfCards = filterMapOfCardsByMode(setOfCards.cards, chosenMode)
//
//            when (chosenMode.modeType) {
//                EModeType.TESTING -> startTestLoop(mapOfCards, chosenMode.fromKey!!, setOfCards.id, user.maxPoint)
//                EModeType.STUDYING -> startStudyingLoop(mapOfCards, chosenMode.fromKey!!)
//                EModeType.SHOW_STATISTICS -> showObjectsStatistic(mapOfCards)
//            }
//            // self-reliant add самостоятельный
//            // recognize add узнавать
//            // benefit убрать или заменить
//            println()
//        }
//
//        logger.error("END")
//    }
//
//    private fun choosePairOfLanguage(setOfPairLanguage: Set<Pair<ELanguage, ELanguage>>): Pair<ELanguage, ELanguage>? {
//        do {
//            println("Choose languages")
//            setOfPairLanguage.forEachIndexed { index, pair -> print("${index + 1} - ${pair.first}/${pair.second}; ") }
//            println("0 - exit;")
//
//            return when (val input = readLine().runCatching { this?.toInt() }.getOrElse { -1 }) {
//                0 -> null
//                in 1..setOfPairLanguage.size -> setOfPairLanguage.elementAt(input!!.minus(1))
//                else -> {
//                    println("I don't understand you")
//                    continue
//                }
//            }
//        } while (true)
//
//    }
//
//    private fun chooseCardTypeOrSetLanguageOrContinue(): ECardType? {
//        val types = ECardType.values().filter { it != ECardType.UNKNOWN }
//
//        do {
//            println("What do you want to learn?")
//            types.forEachIndexed { index, cardType -> print("${index + 1} - ${cardType.name}; ") }
//            println("0 - exit;")
//
//            return when (val input = readLine().runCatching { this?.toInt() }.getOrElse { -1 }) {
//                0 -> null
//                in 1..ECardType.values().size + 1 -> ECardType.values().elementAt(input!!.minus(1))
//                else -> {
//                    println("I don't understand you")
//                    continue
//                }
//            }
//        } while (true)
//    }
//
//    private fun showShortStatisticByCardType(map: MutableMap<String, Card>, cardType: ECardType) {
//        val mapOfCards = map.filter { it.value.type == cardType }.toMutableMap()
//        val cardTypeName = cardType.name.lowercase()
//
//        println("--- Statistics ---")
//        println("${cardTypeName}s: ${mapOfCards.size}")
//        var countCompleted = 0
//        mapOfCards.forEach {
//            if (it.value.statusToKey == ECardStatus.COMPLETED && it.value.statusFromKey == ECardStatus.COMPLETED) countCompleted++
//        }
//        println("Completed ${cardTypeName}s: $countCompleted ")
//
//        var countHard = 0
//        mapOfCards.forEach { if (it.value.statusToKey == ECardStatus.HARD || it.value.statusFromKey == ECardStatus.HARD) countHard++ }
//        println("Hard ${cardTypeName}s: $countHard ")
//        println()
//    }
//
//    private fun chooseMode(languages: Pair<ELanguage, ELanguage>, cardType: ECardType): Mode? {
//        val to = "${languages.first}/${languages.second}"
//        val from = "${languages.second}/${languages.first}"
//
//        do {
//            println("Which mode do you want?:")
//            println("TEST:      1 - all $from;         3 - all $to;")
//            println("TEST:      2 - only hard $from;   4 - only hard $to;")
//            println("Studying:  5 - all;  6 - only hard $from; 7 - only hard $to;")
//            println("Statistics:8 - show ${cardType.name.lowercase()}s sorted by count")
//            println("0 - exit")
//
//            return when (readLine()?.toInt()) {
//                0 -> null
//                1 -> Mode(false, EModeType.TESTING, arrayListOf(ECardStatus.NORMAL, ECardStatus.HARD),cardType)
//                2 -> Mode(false, EModeType.TESTING, arrayListOf(ECardStatus.HARD),cardType)
//                3 -> Mode(true, EModeType.TESTING, arrayListOf(ECardStatus.NORMAL, ECardStatus.HARD),cardType)
//                4 -> Mode(true, EModeType.TESTING, arrayListOf(ECardStatus.HARD),cardType)
//                5 -> Mode(null, EModeType.STUDYING, arrayListOf(ECardStatus.NORMAL, ECardStatus.HARD),cardType)
//                6 -> Mode(false, EModeType.STUDYING, arrayListOf(ECardStatus.HARD),cardType)
//                7 -> Mode(true, EModeType.STUDYING, arrayListOf(ECardStatus.HARD),cardType)
//                8 -> Mode(null, EModeType.SHOW_STATISTICS, arrayListOf(),cardType)
//                else -> {
//                    println("I don't understand you")
//                    continue
//                }
//            }
//        } while (true)
//    }
//
//    private fun filterMapOfCardsByMode(
//        map: MutableMap<String, Card>,
//        chosenMode: Mode
//    ): MutableMap<String, Card> {
//        val mapOfCards = map.filter { it.value.type == chosenMode.cardType }.toMutableMap()
//
//        return when (chosenMode.modeType) {
//            EModeType.TESTING -> {
//                if (chosenMode.fromKey!!) {
//                    mapOfCards.filter { chosenMode.listOfCardStatus.contains(it.value.statusFromKey) }.toMutableMap()
//                } else mapOfCards.filter { chosenMode.listOfCardStatus.contains(it.value.statusToKey) }
//                    .toMutableMap()
//            }
//            EModeType.STUDYING -> {
//                if (chosenMode.fromKey == null) mapOfCards else {
//                    if (chosenMode.fromKey) {
//                        mapOfCards.filter { chosenMode.listOfCardStatus.contains(it.value.statusFromKey) }
//                            .toMutableMap()
//                    } else mapOfCards.filter { chosenMode.listOfCardStatus.contains(it.value.statusToKey) }
//                        .toMutableMap()
//                }
//            }
//            EModeType.SHOW_STATISTICS -> mapOfCards
//        }
//
//    }
//
//    private fun startTestLoop(
//        mapOfCards: MutableMap<String, Card>, translateToNative: Boolean, setOfCardsId: String, userMaxPoint: Int
//    ) {
//        var countMistake = 0
//        var correctAnswer = 0
//        var completedCards = 0
//
//        val listOfPairs = getKeysListOfPairsWithKeyAndValue(mapOfCards, translateToNative)
//
//        listOfPairs.forEach {
//            print("${it.first}:")
//            val input = readln()
//            if (input == "0") return
//
//            val card =
//                if (translateToNative) mapOfCards[it.first] else mapOfCards[it.second] ?: throw Exception("not found")
//
//            val isCorrect = studyingService.checkCard(setOfCardsId, card!!.key9, input, translateToNative)
//            if (isCorrect) {
//                println(it.second)
//                val point = if (translateToNative) card.pointFromKey else card.pointToKey
//
//                if (point >= userMaxPoint - 1) {
//                    println("You learn this ${card.type.name}!")
//                    completedCards++
//                }
//                correctAnswer++
//
//            } else {
//                println("Correct answer:${it.first}:${it.second}")
//                countMistake++
//            }
//
//        }
//
//        println()
//        println("Common count:          ${listOfPairs.size}")
//        println("Amount of mistakes:    $countMistake")
//        println("Amount of correct:     $correctAnswer")
//        println("Amount of completed:   $completedCards")
//    }
//
//    private fun startStudyingLoop(mapOfCards: MutableMap<String, Card>, translateToNative: Boolean) {
//        getKeysListOfPairsWithKeyAndValue(mapOfCards, translateToNative).forEach {
//            print("${it.first}:${it.second}")
//            val input = readln()
//            if (input == "0") return
//        }
//    }
//
//    private fun showObjectsStatistic(mapOfCards: MutableMap<String, Card>) {
//        val line = "_________________________________________________________________________"
//        println(line)
//        mapOfCards.values.sortedByDescending { it.pointToKey + it.pointFromKey }.forEach {
//            System.out.printf("%-40s", "| ${it.key9}:${it.value9}")
//            System.out.printf("%-10s", "| from:${it.statusToKey}(${it.pointToKey})")
//            System.out.printf("%-10s", "| to:${it.statusFromKey}(${it.pointFromKey})\n")
//            println(line)
//        }
//    }
//
//    private fun getKeysListOfPairsWithKeyAndValue(
//        mapOfCards: MutableMap<String, Card>,
//        translateToNative: Boolean
//    ): List<Pair<String, String>> = mapOfCards.map { (key, value) ->
//        if (translateToNative) key to value.value9 else value.value9 to key
//    }.run { if (random) this.shuffled() else this }
//}