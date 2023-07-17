package com.example.memorizing.service

import com.example.memorizing.entity.Cards
import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.ECardType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class StartService(
    private val fileService: FileService, @Value("\${completed.maxPoint}") private val maxPoint: Int
) {
    private val logger: Logger = LogManager.getLogger(StartService::class.java)

    private var active = true
    private var random = true

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        logger.info("START")

        while (active) {
            println("What do you want to learn?")
            println("   1 - Words;  2 - Phrase;   0 - exit")
            val type: ECardType = when (readLine()) {
                "0" -> break
                "1" -> ECardType.WORD
                "2" -> ECardType.PHRASE
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
            val typeName = type.typeName

            println("--- Statistics ---")
            println("${typeName}s: ${Cards.mapOfCards.count { it.value.type == type }}")
            var countCompleted = 0
            Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.COMPLETED && it.value.type == type) countCompleted++ }
            println("Completed ${typeName}s: $countCompleted ")

            var countHard = 0
            Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.HARD && it.value.type == type) countHard++ }
            println("Hard ${typeName}s: $countHard ")

            println("TEST:      1 - all EN/RUS;     2 - only hard EN/RUS;")
            println("Studying:  3 - EN+RUS;         4 - only hard EN+RUS;")
            println("Statistics:5 - show ${typeName}s sorted by count")
            println("0 - exit")
            print("Which type do you select:")

            when (readLine()) {
                "1" -> startTestAllEnRus(type)
                "2" -> startTestHardEnRus(type)
                "3" -> startStudyEnPlusRus(type)
                "4" -> startStudyHardEnPlusRus(type)
                "5" -> printResult(type)
                "0" -> {}
                else -> {
                    logger.info("I don't understand you")
                    continue
                }
            }
        }

        logger.error("END")
    }

    /** "TEST: 1 - all EN/RUS */
    private fun startTestAllEnRus(type: ECardType) {
        println("Translate to russian")
        startTestLoop(ECardStatus.NORMAL, type)
    }

    /** "TEST: 2 - only hard EN/RUS */
    private fun startTestHardEnRus(type: ECardType) {
        println("Translate to russian")
        startTestLoop(ECardStatus.HARD, type)
    }

    /** Studying: 3 - EN+RUS */
    private fun startStudyEnPlusRus(type: ECardType) {
        println("Lets study")
        startStudyingLoop(ECardStatus.NORMAL, type)
    }

    /** Studying: 4 - only hard EN+RUS; */
    private fun startStudyHardEnPlusRus(type: ECardType) {
        println("Lets study")
        startStudyingLoop(ECardStatus.HARD, type)
    }

    /** Statistics: 5 - show cards sorted by count; */
    private fun printResult(type: ECardType) {
        Cards.mapOfCards.values.sortedByDescending { it.point }.forEach {
                if (it.type == type) println("${it.value}:${it.translate}:${it.point}:${it.status}")
            }
    }

    private fun startStudyingLoop(status: ECardStatus, type: ECardType) {
        getKeysByStatusAndType(status, type).forEach {
            print("${it}:${Cards.mapOfCards[it]!!.translate}")
            readln()
        }
    }

    private fun startTestLoop(status: ECardStatus, type: ECardType) {
        val keys = getKeysByStatusAndType(status, type)

        var countMistake = 0
        var savedCards = 0
        var completedCards = 0

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

    private fun getKeysByStatusAndType(status: ECardStatus, type: ECardType): MutableList<String> {
        val keys = mutableListOf<String>()
        when (status) {
            ECardStatus.HARD -> {
                Cards.mapOfCards.forEach { if (it.value.status == ECardStatus.HARD && it.value.type == type) keys.add(it.key) }
            }
            ECardStatus.NORMAL -> {
                Cards.mapOfCards.forEach {
                    if ((it.value.status == ECardStatus.HARD || it.value.status == ECardStatus.NORMAL)
                        && it.value.type == type) keys.add(it.key)
                }
            }
            else -> {}
        }

        return if (random) keys.shuffled().toMutableList() else keys
    }
}