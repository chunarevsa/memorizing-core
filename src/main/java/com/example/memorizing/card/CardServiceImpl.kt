package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl (
    private val cards: CardRepository
) : CardService {

    override fun findCardById(cardId: Int) = cards.findCardById(cardId)
    override fun findListBySetOfCardId(setOfCardId: Int) = cards.findAllBySetOfCardId(setOfCardId)

    override fun addCardToSetOfCard(setOfCardId: Int, cardFieldsDto: CardFieldsDto): Card {
        return cards.saveCard(Card().apply {
            this.cardStockId = setOfCardId
            this.value = cardFieldsDto.value
            this.translate = cardFieldsDto.translate
            this.type = cardFieldsDto.type
            this.pointToNative = cardFieldsDto.pointToNative
            this.pointFromNative = cardFieldsDto.pointFromNative
            this.statusToNative = cardFieldsDto.statusToNative
            this.statusFromNative = cardFieldsDto.statusFromNative
        } )
    }

    override fun saveCard(card: Card) = cards.saveCard(card)

    override fun deleteCard(card: Card) = cards.deleteCard(card)

}
