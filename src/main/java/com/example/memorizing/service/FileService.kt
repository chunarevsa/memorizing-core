package com.example.memorizing.service

import com.example.memorizing.entity.Word
import com.example.memorizing.entity.Words
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
        loadWords()
        readNewWordsFromFile()
    }

    private fun loadWords() {
        val file = File(Words.fileName)
        if (file.exists()) {
            mapper.readValue(file, Words::class.java)
        }
        saveWords()
    }

    fun saveWords() {
        val file = File(Words.fileName)
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, Words)
    }

    private fun readNewWordsFromFile() {
        val file = File("newWords.txt")
        if (!file.exists()) {
            file.createNewFile()
            logger.info("Create newWords.txt")
        }
        val newWords = mutableSetOf<String>()
        FileInputStream(file).bufferedReader().forEachLine { newWords.add(it) }

        val newMapOfWords = mutableMapOf<String, Word>()
        newWords.forEach {
            val split = it.split("\t")
            val string = split.drop(1)[0]
            val translates = mutableListOf<String>()
            if (string.contains(',')) {
                string.split(',').forEach { tr -> translates.add(tr.trim()) }
            } else translates.add(string.trim())

            newMapOfWords[split[0].trim()] = Word(split[0].trim(), translates)
        }

        if (newMapOfWords.isNotEmpty()) {
            newMapOfWords.forEach {
                if (!Words.mapOfWords.keys.contains(it.key)) Words.mapOfWords[it.key] = it.value
            }
            saveWords()
        }
    }
}