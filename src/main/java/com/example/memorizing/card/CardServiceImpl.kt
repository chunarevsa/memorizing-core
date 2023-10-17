package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cards: CardRepository
) : CardService {

    override fun findCardById(cardId: Int) = cards.findCardById(cardId)
    override fun findListByCardStockId(cardStockId: Int) = cards.findAllByCardStockId(cardStockId)

    override fun addCardToCardStock(cardStockId: Int, cardFieldsDto: CardFieldsDto): Card {
        return cards.saveCard(Card().apply {
            this.cardStockId = cardStockId
            this.key9 = cardFieldsDto.key
            this.value9 = cardFieldsDto.value
        })
    }

    override fun saveCard(card: Card) = cards.saveCard(card)

    override fun deleteCard(card: Card) = cards.deleteCard(card)

}
