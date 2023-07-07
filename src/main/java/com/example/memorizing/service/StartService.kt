package com.example.memorizing.service

import com.example.memorizing.entity.EWordStatus
import com.example.memorizing.entity.Words
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

    private var active = true
    private var random = true

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        if (Words.mapOfWords.isEmpty())
            throw Exception("Пустой лист со словами, добавь слова в файл ${"newWords.txt"}")

        while (active) {
            logger.info("START")
            println("Statistics")
            println("Words: ${Words.mapOfWords.size}")
            var countCompletedWords = 0
            Words.mapOfWords.forEach { if (it.value.status == EWordStatus.COMPLETED) countCompletedWords++ }
            println("Completed words: $countCompletedWords ")

            var countHardWords = 0
            Words.mapOfWords.forEach { if (it.value.status == EWordStatus.HARD) countHardWords++ }
            println("Hard words: $countHardWords ")

            println("TEST:      1 - all EN/RUS;     2 - only hard EN/RUS;")
            println("Studying:  3 - EN+RUS;         4 - only hard EN+RUS;")
            println("Statistics:5 - show words sorted by count")
            println("0 - exit")
            print("Which type do you select:")

            when (readLine()) {
                "1" -> startTestAllEnRusWords()
                "2" -> startTestHardEnRusWords()
                "3" -> startStudyEnPlusRus()
                "4" -> startStudyHardEnPlusRus()
                "5" -> printResult()
                "0" -> {
                    active = false
                    break
                }
                else -> {
                    logger.info("Нет такого. Попробуй ещё раз")
                    start()
                }
            }
        }
        logger.error("END")
    }

    /** "TEST: 1 - all EN/RUS */
    private fun startTestAllEnRusWords() {
        println("Translate to russian")
        startTestLoop(EWordStatus.NORMAL)
    }

    /** "TEST: 2 - only hard EN/RUS */
    private fun startTestHardEnRusWords() {
        println("Translate to russian")
        startTestLoop(EWordStatus.HARD)
    }

    /** Studying: 3 - EN+RUS */
    private fun startStudyEnPlusRus() {
        println("Lets study")
        startStudyingLoop(EWordStatus.NORMAL)
    }

    /** Studying: 4 - only hard EN+RUS; */
    private fun startStudyHardEnPlusRus() {
        println("Lets study")
        startStudyingLoop(EWordStatus.HARD)
    }

    /** Statistics: 5 - show words sorted by count; */
    private fun printResult() {
        Words.mapOfWords.values.sortedByDescending { it.point }
            .forEach {
                println("${it.value}:${it.translate}:${it.point}:${it.status}")
            }
    }

    private fun startStudyingLoop(status: EWordStatus) {
        getKeysByStatus(status).forEach {
            print("${it}:${Words.mapOfWords[it]!!.translate}")
            readln()
        }
    }

    private fun startTestLoop(status: EWordStatus) {
        val keys = getKeysByStatus(status)

        var countMistake = 0
        var savedWords = 0
        var completedWords = 0

        keys.forEach {
            print("${it}:")
            val input = readln()
            if (input == "0") return

            val word = Words.mapOfWords[it]

            if (word!!.translate.contains(input)) {
                // correct answer
                if (word.status == EWordStatus.HARD) {
                    word.status = EWordStatus.NORMAL
                    word.point = 0
                    savedWords++
                }
                if (word.point >= maxPoint) {
                    word.point = word.point + 1
                    word.status = EWordStatus.COMPLETED
                    println("You learn this word!")
                    completedWords++
                } else {
                    word.point = word.point + 1
                    print("${word.translate}:${word.point}")
                    println()
                }
                fileService.saveWords()
            } else {
                // wrong answer
                if (word.status == EWordStatus.HARD) {
                    word.point = word.point - 1
                } else word.point = -1

                word.status = EWordStatus.HARD
                fileService.saveWords()
                countMistake++
                println("Opss! Wrong!")
                println("${word.value}:${word.translate}:${word.point}")
            }
        }

        println("Общее количество слов:     ${keys.size}")
        println("Количество ошибок:         $countMistake")
        println("Количество спасенных:      $savedWords")
        println("Количество завершенных:    $completedWords")
    }

    private fun getKeysByStatus(status: EWordStatus): MutableList<String> {
        val keys = mutableListOf<String>()
        when (status) {
            EWordStatus.HARD -> {
                Words.mapOfWords.forEach { if (it.value.status == EWordStatus.HARD) keys.add(it.key) }
            }
            EWordStatus.NORMAL -> {
                Words.mapOfWords.forEach {
                    if (it.value.status == EWordStatus.HARD || it.value.status == EWordStatus.NORMAL) keys.add(it.key)
                }
            }
            else -> {}
        }

        return if (random) keys.shuffled().toMutableList() else keys
    }
}