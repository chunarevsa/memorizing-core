package com.example.memorizing.card

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface CardRepository : Repository<Card, Int> {
    @Transactional(readOnly = true)
    fun findCardById(cardId: Int): Card?
    @Transactional(readOnly = true)
    @Query("select * from card where set_of_card_id = :setOfCardId")
    fun findAllBySetOfCardId(setOfCardId: Int): MutableList<Card>
    @Transactional
    fun saveCard(card: Card): Card
    @Transactional
    fun deleteCard(card: Card)
}
