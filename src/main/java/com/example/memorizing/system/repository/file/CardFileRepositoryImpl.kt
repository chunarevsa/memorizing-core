package com.example.memorizing.system.repository.file

import com.example.memorizing.card.Card
import com.example.memorizing.entity.ECardType
import com.example.memorizing.entity.ELanguage
import com.example.memorizing.cardStock.CardStock
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileInputStream

@Repository
class CardFileRepositoryImpl : AFileRepository() {
    private val logger: Logger = LogManager.getLogger(CardFileRepositoryImpl::class.java)

    override val pref: String = "set_of_cards"
    override val entity: Class<*> = CardStock::class.java

    private val prefForNewObjectsFile: String = "new_"

    init {
        readNewObjectFromFile()
        // validateCardsStatusByMaxPoint
    }

    override fun findSetOfCardsById(id: String): CardStock {
        val files = findFilesByPref(pref)
        val cardStocks = files?.map { load(it.nameWithoutExtension.substring(pref.length)) as CardStock }
        return cardStocks?.find { it.id == id } ?: throw Exception("not found")
    }

    override fun saveSetOfCards(cardStock: CardStock) {
        save(cardStock, "_${cardStock.pair!!.first}_${cardStock.pair.second}")
    }

    override fun saveCard(setOfCardsId: String, card: Card) {
        val setOfCards = findSetOfCardsById(setOfCardsId)
        setOfCards.cards.replace(card.value, card)
        saveSetOfCards(setOfCards)
    }

    override fun createNewFilesForSet(pair: Pair<ELanguage, ELanguage>) {
        ECardType.values().forEach {
            if (it != ECardType.UNKNOWN) {
                val file = File(FILE_PATH + "$prefForNewObjectsFile${it}_${pair.first}_${pair.second}.txt")
                if (!file.exists()) file.createNewFile()
            }
        }
    }

    private fun readNewObjectFromFile() {
        val files = findFilesByPref(prefForNewObjectsFile)
        val listOfFilesName =
            files?.map {
                it.nameWithoutExtension.substring(prefForNewObjectsFile.length)
            }

        // example: words_ENG_RUS, phrase_DEU_RUS
        listOfFilesName?.forEach {
            val split = it.split("_")

            val cardType = ECardType.valueOf(split[0])
            val firstLanguage = ELanguage.valueOf(split[1])
            val nativeLanguage = ELanguage.valueOf(split[2])

            // set_of_cards_ENG_RUS
            val postFix = "_${firstLanguage}_${nativeLanguage}"
            val cardStock = load(dynamicPartOfName = postFix) as CardStock

            val fileForNewObject = File("$FILE_PATH$prefForNewObjectsFile$cardType$postFix.txt")

            val strings = mutableSetOf<String>()
            FileInputStream(fileForNewObject).bufferedReader().forEachLine { str -> strings.add(str) }

            var amountOfAddingCard = 0
            strings.forEach { str ->
                val split2 = str.split("\t")
                cardStock.cards[split2[0].lowercase()] = Card().apply {
                    this.value = split2[0].lowercase()
                    this.translate = split2.drop(1).toString().lowercase()
                    this.type = cardType
                }
                amountOfAddingCard++
            }

            saveSetOfCards(cardStock)
            fileForNewObject.delete()
            fileForNewObject.createNewFile()

            if (amountOfAddingCard > 0) logger.info("Added $amountOfAddingCard new $cardType in $pref$postFix")
        }
    }
}