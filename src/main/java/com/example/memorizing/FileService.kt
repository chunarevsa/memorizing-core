package com.example.memorizing

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream

@Service
class FileService {
    private val logger: Logger = LogManager.getLogger(FileService::class.java)

    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)

    init {
        loadWords(Words.fileName)
        loadWords(HardWords.fileName)
        loadWords(CompletedWords.fileName)
        readNewWordsFromFile()
    }

    private fun loadWords(nameFile: String) {
        val file = File(nameFile)
        if (file.exists()) {
            when (nameFile) {
                HardWords.fileName -> mapper.readValue(file, HardWords::class.java)
                CompletedWords.fileName -> mapper.readValue(file, CompletedWords::class.java)
                Words.fileName-> mapper.readValue(file, Words::class.java)
            }
        }
        saveWords(nameFile)
    }

    fun saveWords(nameFile: String) {
        val file = File(nameFile)
        file.mkdirs()
        file.delete()
        when (nameFile) {
            HardWords.fileName -> mapper.writeValue(file, HardWords)
            CompletedWords.fileName ->  mapper.writeValue(file, CompletedWords)
            Words.fileName-> mapper.writeValue(file, Words)
        }
    }

    private fun readNewWordsFromFile() {
        val file = File("newWords.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        val newWords = mutableSetOf<String>()
        FileInputStream(file).bufferedReader().forEachLine { newWords.add(it) }

        val newMapOfWords = mutableMapOf<String, Word>()
        newWords.forEach {
            val split = it.split("\t")
            newMapOfWords[split[0]] = Word(split[0], split.drop(1).toString(),0)
        }

        if (newMapOfWords.isNotEmpty()) {
            newMapOfWords.forEach { Words.mapOfWords[it.key] = it.value }
            saveWords(Words.fileName)
        }

    }
}