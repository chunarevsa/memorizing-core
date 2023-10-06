package com.example.memorizing.repository

import com.example.memorizing.entity.Card
import com.example.memorizing.entity.SetOfCards
import org.springframework.stereotype.Repository

@Repository
class CardFileRepositoryImpl : AFileRepository(), CardRepository {

    override val fileName: String = "SetOfCards"
    override val entity: Class<*> = SetOfCards::class.java

    fun getSetOfCardsByUsername(username: String): SetOfCards {
        TODO("Not yet implemented")
    }

    override fun findSetOfCardsByUsername(username: String): List<SetOfCards> {
        TODO("Not yet implemented")
    }

    override fun findSetOfCardsById(id: String): SetOfCards {
        TODO("Not yet implemented")
    }

    override fun saveSetOfCards(setOfCarfs: SetOfCards) {
        save(setOfCarfs)
    }

    override fun saveCard(setOfCardsId: String, card: Card) {
        val setOfCards = findSetOfCardsById(setOfCardsId)
        setOfCards.mapOfCards.replace(card.value, card)
        saveSetOfCards(setOfCards)
    }


}