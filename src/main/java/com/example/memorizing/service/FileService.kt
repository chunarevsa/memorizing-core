package com.example.memorizing.service

import com.example.memorizing.entity.ECardType
import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.Card
import com.example.memorizing.entity.Cards
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
        loadCards()
        validateCards()
        readCardsFromFile(FILE_NAME_FOR_NEW_WORDS)
        readCardsFromFile(FILE_NAME_FOR_NEW_PHRASES)
    }

    fun saveCards() {
        val file = File(Cards.fileName)
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, Cards)
    }

    private fun loadCards() {
        val file = File(Cards.fileName)
        if (file.exists()) {
            mapper.readValue(file, Cards::class.java)
        }
        saveCards()
    }

    private fun readCardsFromFile(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            file.createNewFile()
            logger.info("Create $fileName")
        }

        val newCards = mutableSetOf<String>()
        FileInputStream(file).bufferedReader().forEachLine { newCards.add(it) }

        val newMapOfCards = mutableMapOf<String, Card>()
        newCards.forEach {
            val split = it.split("\t")
            val cardType: ECardType = when (fileName) {
                FILE_NAME_FOR_NEW_WORDS -> ECardType.WORD
                FILE_NAME_FOR_NEW_PHRASES -> ECardType.PHRASE
                else -> { throw Exception("Неверное имя файла")}
            }
            newMapOfCards[split[0]] = Card(split[0], split.drop(1).toString(), cardType)
        }

        if (newMapOfCards.isNotEmpty()) {
            newMapOfCards.forEach {
                if (!Cards.mapOfCards.keys.contains(it.key)) Cards.mapOfCards[it.key] = it.value
            }
            saveCards()
        }
    }

    private fun validateCards() {
        Cards.mapOfCards.forEach {
            val card = it.value
            if (card.point < 0 && card.status != ECardStatus.HARD) it.value.status = ECardStatus.HARD
            if (card.point in 0 .. maxPoint && card.status != ECardStatus.NORMAL) it.value.status = ECardStatus.NORMAL
            if (card.point > maxPoint && card.status != ECardStatus.COMPLETED) it.value.status = ECardStatus.COMPLETED
        }
        saveCards()
    }
}