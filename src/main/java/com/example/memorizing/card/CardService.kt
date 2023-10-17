package com.example.memorizing.card

import org.springframework.transaction.annotation.Transactional

interface CardService {

    @Transactional(readOnly = true)
    fun findListByCardStockId(cardStockId: Int): MutableList<Card>
    @Transactional(readOnly = true)
    fun findCardById(cardId: Int): Card?
    @Transactional
    fun addCardToCardStock(cardStockId: Int, cardFieldsDto: CardFieldsDto): Card
    @Transactional
    fun saveCard(card: Card): Card
    @Transactional
    fun deleteCard(card: Card)
}
