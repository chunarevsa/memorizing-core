package com.example.memorizing.service

import com.example.memorizing.entity.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
//
//@Service
//class StartService(
//    private val fileService: FileService,
//) {
//    private val logger: Logger = LogManager.getLogger(StartService::class.java)
//    private var random = true
//
//    fun start(username: String = "Sergei") {
//        logger.info("START")
//        fileService.getUserByUsername(username)
//
//
//        var languageMode: Pair<ELanguage, ELanguage>
//
//        while (true) {
//            println("What do you want to learn?")
//            println("   1 - Words;  2 - Phrase; 3 - setSecondLanguage   0 - exit")
//            val cardType: ECardType = when (readLine()) {
//                "0" -> break
//                "1" -> ECardType.WORD
//                "2" -> ECardType.PHRASE
//                "3" -> {
//                    println("Choose your first language")
//                    getLanguage()?.let { firstLanguage = it }
//                    println("Choose your first language")
//                    getLanguage()?.let { secondLanguage = it }
//                    continue
//                }
//                else -> {
//                    logger.info("I don't understand you")
//
//                    continue
//                }
//            }
//            val cardTypeName = cardType.typeName
//
//            val mapOfCards = getCardsByLanguageType(languageType)
//
//            println("--- Statistics ---")
//            println("${cardTypeName}s: ${mapOfCards.count { it.value.type == cardType }}")
//            var countCompleted = 0
//            mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.COMPLETED && it.value.type == cardType) countCompleted++ }
//            println("Completed ${cardTypeName}s: $countCompleted ")
//
//            var countHard = 0
//            mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.HARD && it.value.type == cardType) countHard++ }
//            println("Hard ${cardTypeName}s: $countHard ")
//
//            println("TEST:      1 - all $secondLanguage/$firstLanguage;         3 - all $firstLanguage/$secondLanguage  ")
//            println("TEST:      2 - only hard $secondLanguage/$firstLanguage;   4 - only hard $firstLanguage/$secondLanguage;")
//
//            println("Studying:  5 - all;                6 - only hard;")
//
//            println("Statistics:7 - show ${cardTypeName}s sorted by count")
//
//            println("0 - exit")
//            print("Which type do you select?:")
//
//            when (readLine()) {
//                "1" -> startTestLoop(ECardStatus.NORMAL, cardType, ELanguageMode.ENG_RUS)
//                "2" -> startTestLoop(ECardStatus.NORMAL, cardType, ELanguageMode.RUS_ENG)
//
//                "3" -> startTestLoop(ECardStatus.HARD, cardType, ELanguageMode.ENG_RUS)
//                "4" -> startTestLoop(ECardStatus.HARD, cardType, ELanguageMode.RUS_ENG)
//
//                "5" -> startStudyingLoop(ECardStatus.NORMAL, cardType)
//                "6" -> startStudyingLoop(ECardStatus.HARD, cardType)
//
//                "7" -> {
//                    mapOfCards.values.sortedByDescending { it.pointFromNative }.forEach {
//                        if (it.type == cardType) println("${it.value}:${it.translate}:${it.pointFromNative}:${it.statusFromNative}")
//                    }
//                }
//                "0" -> {}
//                else -> {
//                    logger.info("I don't understand you")
//                    continue
//                }
//            }
//        }
//
//        logger.error("END")
//    }
//
//    private fun startStudyingLoop(status: ECardStatus, cardType: ECardType) {
//        getKeysByStatusAndCardType(status, cardType).forEach {
//            print("${it}:${getCardsByLanguageType(languageType)[it]!!.translate}")
//            readln()
//        }
//    }
//
//    fun getCardsByLanguageType(languageType: ELanguageMode): MutableMap<String, Card> {
//        val languageCards =
//            Cards.listOfCardsByELanguageType.find { languageType == it.languageType } ?: throw Exception("d")
//
//        return languageCards.mapOfCards
//    }
//
//    private fun startTestLoop(status: ECardStatus, cardType: ECardType, languageType: ELanguageMode) {
//        val keys = getKeysByStatusAndCardType(status, cardType)
//
//        var countMistake = 0
//        var savedCards = 0
//        var completedCards = 0
//
//        // Сделать через HashMap (какой язык такой и ключ)
//        //
//        // Поиск и изменение сделать зависимым от типа языка
//
//        keys.forEach {
//            print("${it}:")
//            val input = readln()
//            if (input == "0") return
//
//            val card = getCardsByLanguageType(languageType)[it]
//
//            if (card!!.translate.contains(input)) {
//                // correct answer
//                if (card.statusFromNative == ECardStatus.HARD) {
//                    card.statusFromNative = ECardStatus.NORMAL
//                    card.pointFromNative = 0
//                    savedCards++
//                }
//                if (card.pointFromNative >= maxPoint) {
//                    card.pointFromNative = card.pointFromNative + 1
//                    card.statusFromNative = ECardStatus.COMPLETED
//                    println("You learn this ${card.type.name}!")
//                    completedCards++
//                } else {
//                    card.pointFromNative = card.pointFromNative + 1
//                    print("${card.translate}:${card.pointFromNative}")
//                    println()
//                }
//                fileService.saveCards()
//            } else {
//                // wrong answer
//                if (card.statusFromNative == ECardStatus.HARD) {
//                    card.pointFromNative = card.pointFromNative - 1
//                } else card.pointFromNative = -1
//
//                card.statusFromNative = ECardStatus.HARD
//                fileService.saveCards()
//                countMistake++
//                println("Opss! Wrong!")
//                println("${card.value}:${card.translate}:${card.pointFromNative}")
//            }
//        }
//
//        println("Common count:          ${keys.size}")
//        println("Amount of mistakes:    $countMistake")
//        println("Amount of saved:       $savedCards")
//        println("Amount of completed:   $completedCards")
//    }
//
//    private fun getKeysByStatusAndCardType(status: ECardStatus, cardType: ECardType): MutableList<String> {
//        val keys = mutableListOf<String>()
//        val mapOfCards = getCardsByLanguageType(languageType)
//
//        when (status) {
//            ECardStatus.HARD -> {
//                mapOfCards.forEach { if (it.value.statusFromNative == ECardStatus.HARD && it.value.type == cardType) keys.add(it.key) }
//            }
//            ECardStatus.NORMAL -> {
//                mapOfCards.forEach {
//                    if ((it.value.statusFromNative == ECardStatus.HARD || it.value.statusFromNative == ECardStatus.NORMAL)
//                        && it.value.type == cardType) keys.add(it.key)
//                }
//            }
//            else -> {}
//        }
//
//        return if (random) keys.shuffled().toMutableList() else keys
//    }
//
//    private fun getLanguage(): ELanguage? {
//        val listOfLanguage = ELanguage.values().toMutableList()
//        listOfLanguage.forEach { println("${it.ordinal + 1} - ${it.name}") }
//
//        return try {
//            listOfLanguage[readLine()!!.toInt()]
//        } catch (e: Exception) {
//            logger.info("I don't understand you")
//            null
//        }
//    }
//}