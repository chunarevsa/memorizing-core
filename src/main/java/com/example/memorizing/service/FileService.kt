package com.example.memorizing.service

import com.example.memorizing.entity.EObjectType
import com.example.memorizing.entity.EWordStatus
import com.example.memorizing.entity.Word
import com.example.memorizing.entity.Words
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream

@Service
class FileService(
    @Value("\${completed.maxPoint}")
    private val maxPoint: Int
) {
    companion object {
        const val FILE_NAME_FOR_NEW_WORDS = "newWords.txt"
        const val FILE_NAME_FOR_NEW_PHRASES = "newPhrases.txt"
    }

    private val logger: Logger = LogManager.getLogger(FileService::class.java)

    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)

    init {
        loadWords()
        validateWords()
        readObjectsFromFile(FILE_NAME_FOR_NEW_WORDS)
        readObjectsFromFile(FILE_NAME_FOR_NEW_PHRASES)
    }

    fun saveWords() {
        val file = File(Words.fileName)
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, Words)
    }

    private fun loadWords() {
        val file = File(Words.fileName)
        if (file.exists()) {
            mapper.readValue(file, Words::class.java)
        }
        saveWords()
    }

    private fun readObjectsFromFile(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            file.createNewFile()
            logger.info("Create $fileName")
        }
        val newWords = mutableSetOf<String>()
        FileInputStream(file).bufferedReader().forEachLine { newWords.add(it) }

        val newMapOfWords = mutableMapOf<String, Word>()
        newWords.forEach {
            val split = it.split("\t")
            val objectType: EObjectType = when (fileName) {
                FILE_NAME_FOR_NEW_WORDS -> EObjectType.WORD
                FILE_NAME_FOR_NEW_PHRASES -> EObjectType.PHRASE
                else -> { throw Exception("Неверное имя файла")}
            }
            newMapOfWords[split[0]] = Word(split[0], split.drop(1).toString(), objectType)
        }

        if (newMapOfWords.isNotEmpty()) {
            newMapOfWords.forEach {
                if (!Words.mapOfWords.keys.contains(it.key)) Words.mapOfWords[it.key] = it.value
            }
            saveWords()
        }
    }

    private fun validateWords() {
        Words.mapOfWords.forEach {
            val word = it.value
            if (word.point < 0 && word.status != EWordStatus.HARD) it.value.status = EWordStatus.HARD
            if (word.point in 0 .. maxPoint && word.status != EWordStatus.NORMAL) it.value.status = EWordStatus.NORMAL
            if (word.point > maxPoint && word.status != EWordStatus.COMPLETED) it.value.status = EWordStatus.COMPLETED
        }
        saveWords()
    }
}