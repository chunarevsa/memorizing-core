package com.example.memorizing.service

import com.example.memorizing.entity.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
//
//@Service
//class FileService {
//    companion object {
//        const val PREF_NEW_WORDS_FILE = "newWords"
//        const val PREF_NEW_PHRASES_FILE = "newPhrases"
//
//        const val FILE_PATH = "src/main/resources/data"
//    }
//
//    private val logger: Logger = LogManager.getLogger(FileService::class.java)
//
//    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
//
//    fun getUserByUsername(username: String) {
//        loadUser(username)
//    }
//
//    private fun loadUser(username: String): User {
//        val file = File( FILE_PATH + FILE_NAME_TO_USER)
//
//        val userContainer: UserContainer
//        if (file.exists()) {
//            userContainer = mapper.readValue(file, UserContainer::class.java)
//            userContainer.users.find { it.username == username }
//        }
//
//    }
//
//    init {
//        loadCards()
//        validateCardsStatusByPoint()
//        val listOfSecondLanguages = ELanguage.getPairsOfLanguage().map {
//            if (secondLanguage != it.first) it.first else it.second
//        }
//
//        listOfSecondLanguages.forEach {
//            readCardsFromFile(PREF_NEW_WORDS_FILE, it)
//            readCardsFromFile(PREF_NEW_PHRASES_FILE, it)
//        }
//
//    }
//
//    fun saveCards() {
//        val file = File(Cards.fileName)
//        file.mkdirs()
//        file.delete()
//        mapper.writeValue(file, Cards)
//    }
//
//    private fun loadCards() {
//        val file = File(Cards.fileName)
//        if (file.exists()) {
//            mapper.readValue(file, Cards::class.java)
//        }
//        saveCards()
//    }
//
//    private fun readCardsFromFile(pref: String, firstLanguage: ELanguage) {
//        val fileName = pref + "${firstLanguage.name}_${secondLanguage.name}"
//
//        val file = File(fileName)
//        if (!file.exists()) {
//            file.createNewFile()
//            logger.info("Create ${file.name}")
//            return
//        }
//
//        val newCards = mutableSetOf<String>()
//        FileInputStream(file).bufferedReader().forEachLine { newCards.add(it) }
//
//        val newMapOfCards = mutableMapOf<String, Card>()
//        val cardType: ECardType = when (pref) {
//            PREF_NEW_WORDS_FILE -> ECardType.WORD
//            PREF_NEW_PHRASES_FILE -> ECardType.PHRASE
//            else -> return
//        }
//
//        newCards.forEach {
//            val split = it.split("\t")
//            newMapOfCards[split[0]] = Card(split[0], split.drop(1).toString(), cardType)
//        }
//
//        if (newMapOfCards.isNotEmpty()) {
//            var amountOfAddingCard = 0
//            newMapOfCards.forEach {
//                val cards = getCardsByFirstLanguage(firstLanguage)
//                if (!cards.keys.contains(it.key)) {
//                    cards[it.key] = it.value
//                    amountOfAddingCard++
//                }
//            }
//
//            logger.info("Added $amountOfAddingCard new ${cardType.typeName}")
//            saveCards()
//            file.delete()
//            file.createNewFile()
//        }
//    }
//
//    private fun validateCardsStatusByPoint() {
//        Cards.listOfCardsByELanguageType.forEach {
//            it.mapOfCards.forEach {card ->
//                if (card.point < 0 && card.status != ECardStatus.HARD) it.value.status = ECardStatus.HARD
//                if (card.point in 0 .. maxPoint && card.status != ECardStatus.NORMAL) it.value.status = ECardStatus.NORMAL
//                if (card.point > maxPoint && card.status != ECardStatus.COMPLETED) it.value.status = ECardStatus.COMPLETED
//            }
//        }
//
//        saveCards()
//    }
//
//    fun getCardsByFirstLanguage(firstLanguage: ELanguage): MutableMap<String, Card> {
//        val languageCards =
//            Cards.listOfCardsByELanguageType.find { it.languageType.first == firstLanguage } ?: throw Exception("d")
//
//        return languageCards.mapOfCards
//    }
//
//}