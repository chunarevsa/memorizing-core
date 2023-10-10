package com.example.memorizing.repository

import com.example.memorizing.entity.Card
import com.example.memorizing.entity.ELanguage
import com.example.memorizing.entity.SetOfCards

interface CardRepository {
    fun createNewFilesForSet(pair: Pair<ELanguage, ELanguage>)
    fun findSetOfCardsById(id: String): SetOfCards
    fun saveSetOfCards(setOfCards: SetOfCards)
    fun saveCard(setOfCardsId: String, card: Card)
}
