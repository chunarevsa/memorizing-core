package com.example.memorizing.card

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface CardRepository : Repository<Card, Int> {
//    @Transactional(readOnly = true)
    fun findCardById(cardId: Int): Card?
//    @Transactional(readOnly = true)
    @Query("select * from card where card_stock_id = :cardStockId")
    fun findAllByCardStockId(cardStockId: Int): MutableList<Card>
//    @Transactional
    fun save(card: Card): Card
//    @Transactional
    fun delete(card: Card)
}
