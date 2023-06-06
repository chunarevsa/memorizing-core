package com.example.memorizing

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class StartService(
    private var fileService: FileService
) {
    private val logger: Logger = LogManager.getLogger(StartService::class.java)

    private var active = true
    private var random = false

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        if (Words.mapOfWords.isEmpty())
            throw Exception("Пустой лист со словами, добавь слова в файл ${"newWords.txt"}")

        while (active) {
            println("Statistics")
            println("Words: ${Words.mapOfWords.size}")
            println("HardWords: ${HardWords.mapOfWords.size}")
            println("CompletedWords: ${CompletedWords.mapOfWords.size}")

            logger.info("START")
            println("TEST:      1 - all EN/RUS;     2 - only hard EN/RUS;")
            println("Studying:  3 - EN+RUS;         4 - only hard EN+RUS;")
            println("0 - exit")
            print("Which type do you select:")

            when (readLine()) {
                "1" -> startTestAllEnRusWords()
                "2" -> startTestHardEnRusWords()
                "3" -> startStudyEnPlusRus()
                "4" -> startStudyHardEnPlusRus()
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
        startTestLoop(Words.mapOfWords)
    }

    /** "TEST: 2 - only hard EN/RUS */
    private fun startTestHardEnRusWords() {
        println("Translate to russian")
        startTestLoop(HardWords.mapOfWords)
    }

    /** Studying: 3 - EN+RUS */
    private fun startStudyEnPlusRus() {
        println("Lets study")
        startStudyingLoop(Words.mapOfWords)
    }

    /** Studying: 4 - only hard EN+RUS; */
    private fun startStudyHardEnPlusRus() {
        println("Lets study")
        startStudyingLoop(HardWords.mapOfWords)
    }

    private fun startStudyingLoop(words: MutableMap<String, Word>) {
        val keys = words.keys.shuffled()
        keys.forEach {
            print("${it}:${words[it]!!.translate}")
            readln()
        }
    }

    private fun startTestLoop(words: MutableMap<String, Word>) {
        val keys = words.keys
        if (random) keys.shuffled()

        var countMistake = 0
        var savedWords = 0
        var completedWords = 0

        keys.forEach {
            print("${it}:")
            val input = readln()
            if (input == "0") return
            val word = words[it]

            if (!word!!.translate.contains(input)) {
                // add to HardWords
                HardWords.mapOfWords[word.value] = word
                fileService.saveWords(HardWords.fileName)

                // reset counter
                Words.mapOfWords[word.value] = word.apply {
                    this.counterOfSuccessful = 0
                }
                fileService.saveWords(Words.fileName)

                countMistake++
                println("Упс! Неверно")
                println("Перевод: ${word.translate}")
            } else if (HardWords.mapOfWords.containsKey(it)) {
                // remove this word from HardWords
                HardWords.mapOfWords.remove(it)
                fileService.saveWords(HardWords.fileName)

                if (word.counterOfSuccessful >= 10) {
                    CompletedWords.mapOfWords[word.value] = word
                    fileService.saveWords(CompletedWords.fileName)

                    HardWords.mapOfWords.remove(it)
                    fileService.saveWords(HardWords.fileName)

                    Words.mapOfWords.remove(it)
                    fileService.saveWords(Words.fileName)
                    completedWords++

                } else {
                    // increase counter
                    Words.mapOfWords[word.value] = word.apply {
                        this.counterOfSuccessful = this.counterOfSuccessful++
                    }
                    fileService.saveWords(Words.fileName)
                }
                savedWords++
            }
        }

        println("Общее количество слов: ${keys.size}")
        println("Количество ошибок:     $countMistake")
        println("Количество спасенных:  $savedWords")
        println("Количество спасенных:  $completedWords")
    }
}