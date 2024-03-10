package com.memorizing.core.card

import com.memorizing.core.card.rest.api.CardFieldsDto
import com.memorizing.core.card.rest.api.TestResultDto
import com.memorizing.commonapi.model.CheckCardDto

interface CardService {
    fun findAllByCardStockId(cardStockId: Int): List<Card>
    fun findById(cardId: Int): Card
    fun create(fields: CardFieldsDto): Card
    fun update(cardId: Int, fields: CardFieldsDto): Card
    fun delete(card: Card)
    fun deleteById(cardId: Int)
    fun checkCard(cardId: Int, checkCardDto: CheckCardDto): TestResultDto
}
