package com.example.memorizing.card

import com.example.memorizing.card.api.CardFieldsDto
import com.example.memorizing.card.api.CheckCardDto
import com.example.memorizing.card.api.TestResultDto

interface CardService {
    fun findAllByCardStockId(cardStockId: Int): List<Card>
    fun findById(cardId: Int): Card
    fun create(fields: CardFieldsDto): Card
    fun update(cardId: Int, fields: CardFieldsDto): Card
    fun delete(card: Card)
    fun deleteById(cardId: Int)
    fun checkCard(cardId: Int, checkCardDto: CheckCardDto): TestResultDto
}
