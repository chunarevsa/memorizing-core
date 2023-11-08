package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cards: CardRepository
) : CardService {

    override fun findCardById(cardId: Int) = cards.findCardById(cardId)
    override fun findListByCardStockId(cardStockId: Int) = cards.findAllByCardStockId(cardStockId)

    override fun createCard(cardFieldsDto: CardFieldsDto): Card {
        val card = Card(
            cardFieldsDto.cardStockId,
            cardFieldsDto.cardKey,
            cardFieldsDto.cardValue,
            statusToKey = if (cardFieldsDto.onlyFromKey == true) ECardStatus.NOT_SUPPORTED else ECardStatus.NORMAL
        )

        return cards.save(card)
    }

    override fun saveCard(card: Card) = cards.save(card)

    override fun deleteCard(card: Card) = cards.delete(card)
    override fun checkCard(card: Card, checkCardDto: CheckCardDto): TestResultDto {
        val userValue = checkCardDto.userValue!!.lowercase();
        val cardKey: String = card.cardKey!!.lowercase()
        val cardValue: String = card.cardValue!!.lowercase()

        val isFromKey: Boolean = checkCardDto.fromKey!!
        var isAnswerToOtherCard = false

        // translateToNative -> isFromKey == true
        val isCorrect: Boolean = if (isFromKey) {
            cardValue.contains(userValue)
        } else {
            if (cardKey.contains(userValue)) true else {
                // We need check out in the other cards
                val cards = findListByCardStockId(card.cardStockId!!)

                val cardValues = cardValue.removePrefix("[").removeSuffix("]")
                    .split(',').map { it.trim() }.toMutableList()

                val cardsContainingUserValue = cards.filter { it.cardKey!!.lowercase().contains(userValue) }
                val newCard: Card? = cardsContainingUserValue.find { newCard ->
                    val cardValuesByUserValue = newCard.cardValue!!.lowercase().removePrefix("[").removeSuffix("]")
                        .split(',').map { it.trim() }

                    cardValues.retainAll(cardValuesByUserValue)
                    cardValues.isNotEmpty()
                }

                if (newCard != null) {
                    isAnswerToOtherCard = true
                    true
                } else false
            }
        }

        if (isCorrect) {
            card.increasePoint(isFromKey, checkCardDto.maxPoint!!)
        } else card.decreasePoint(isFromKey)
        cards.save(card)

        return TestResultDto(
            rightAnswer = isCorrect,
            answerToOtherCard = isAnswerToOtherCard,
            CardDto(
                id = card.id,
                cardKey = card.cardKey,
                cardValue = card.cardValue,
                pointFromKey = card.pointFromKey,
                pointToKey = card.pointToKey,
                statusFromKey = card.statusFromKey,
                statusToKey = card.statusToKey
            )
        )
    }


}
