package com.example.memorizing.service

import com.example.memorizing.entity.Cards
import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.ECardType
import com.example.memorizing.entity.ELanguageType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class StartService(
    private val fileService: FileService,
    @Value("\${completed.maxPoint}")
    private val maxPoint: Int
) {
    private val logger: Logger = LogManager.getLogger(StartService::class.java)

    private var random = true

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        logger.info("START")

        while (true) {
            println("What do you want to learn?")
            println("   1 - Words;  2 - Phrase;   0 - exit")
            val cardType: ECardType = when (readLine()) {
                "0" -> break
                "1" -> ECardType.WORD
                "2" -> ECardType.PHRASE
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
            val cardTypeName = cardType.typeName

            println("--- Statistics ---")
            println("${cardTypeName}s: ${Cards.mapOfCards.count { it.value.type == cardType }}")
            var countCompleted = 0
            Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.COMPLETED && it.value.type == cardType) countCompleted++ }
            println("Completed ${cardTypeName}s: $countCompleted ")

            var countHard = 0
            Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.HARD && it.value.type == cardType) countHard++ }
            println("Hard ${cardTypeName}s: $countHard ")

            println("TEST:      1 - all EN/RUS;         3 - all RUS/EN  ")
            println("TEST:      2 - only hard EN/RUS;   4 - only hard RUS/EN;")
            
            println("Studying:  5 - all;                6 - only hard;")
            
            println("Statistics:7 - show ${cardTypeName}s sorted by count")
            
            println("0 - exit")
            print("Which type do you select?:")

            when (readLine()) {
                "1" -> startTestLoop(ECardStatus.NORMAL, cardType, ELanguageType.EN_RUS)
                "2" -> startTestLoop(ECardStatus.NORMAL, cardType, ELanguageType.RUS_EN)

                "3" -> startTestLoop(ECardStatus.HARD, cardType, ELanguageType.EN_RUS)
                "4" -> startTestLoop(ECardStatus.HARD, cardType, ELanguageType.RUS_EN)
                
                "5" -> startStudyingLoop(ECardStatus.NORMAL, cardType)
                "6" -> startStudyingLoop(ECardStatus.HARD, cardType)
                
                "7" -> {
                    Cards.mapOfCards.values.sortedByDescending { it.point }.forEach {
                        if (it.type == cardType) println("${it.value}:${it.translate}:${it.point}:${it.status}")
                    }
                }
                "0" -> {}
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
        }

        logger.error("END")
    }

    private fun startStudyingLoop(status: ECardStatus, cardType: ECardType) {
        getKeysByStatusAndCardType(status, cardType).forEach {
            print("${it}:${Cards.mapOfCards[it]!!.translate}")
            readln()
        }
    }

    private fun startTestLoop(status: ECardStatus, cardType: ECardType, languageType: ELanguageType) {
        val keys = getKeysByStatusAndCardType(status, cardType)

        var countMistake = 0
        var savedCards = 0
        var completedCards = 0

        // Сделать через HashMap (какой язык такой и ключ)
        //
        // Поиск и изменение сделать зависимым от типа языка

        keys.forEach {
            print("${it}:")
            val input = readln()
            if (input == "0") return

            val card = Cards.mapOfCards[it]

            if (card!!.translate.contains(input)) {
                // correct answer
                if (card.status == ECardStatus.HARD) {
                    card.status = ECardStatus.NORMAL
                    card.point = 0
                    savedCards++
                }
                if (card.point >= maxPoint) {
                    card.point = card.point + 1
                    card.status = ECardStatus.COMPLETED
                    println("You learn this ${card.type.name}!")
                    completedCards++
                } else {
                    card.point = card.point + 1
                    print("${card.translate}:${card.point}")
                    println()
                }
                fileService.saveCards()
            } else {
                // wrong answer
                if (card.status == ECardStatus.HARD) {
                    card.point = card.point - 1
                } else card.point = -1

                card.status = ECardStatus.HARD
                fileService.saveCards()
                countMistake++
                println("Opss! Wrong!")
                println("${card.value}:${card.translate}:${card.point}")
            }
        }

        println("Common count:          ${keys.size}")
        println("Amount of mistakes:    $countMistake")
        println("Amount of saved:       $savedCards")
        println("Amount of completed:   $completedCards")
    }

    private fun getKeysByStatusAndCardType(status: ECardStatus, cardType: ECardType): MutableList<String> {
        val keys = mutableListOf<String>()
        when (status) {
            ECardStatus.HARD -> {
                Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.HARD && it.value.type == cardType) keys.add(it.key) }
            }
            ECardStatus.NORMAL -> {
                Cards.mapOfCards.forEach {
                    if ((it.value.status == ECardStatus.HARD || it.value.status == ECardStatus.NORMAL)
                        && it.value.type == cardType) keys.add(it.key)
                }
            }
            else -> {}
        }

        return if (random) keys.shuffled().toMutableList() else keys
    }
}