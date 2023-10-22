package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cards: CardRepository
) : CardService {

    override fun findCardById(cardId: Int) = cards.findCardById(cardId)
    override fun findListByCardStockId(cardStockId: Int) = cards.findAllByCardStockId(cardStockId)

    override fun createCard(cardFieldsDto: CardFieldsDto): Card {
        return cards.save(Card().apply {
            this.cardStockId = cardFieldsDto.cardStockId
            this.cardKey = cardFieldsDto.cardKey
            this.cardValue = cardFieldsDto.cardValue
        })
    }

    override fun saveCard(card: Card) = cards.save(card)

    override fun deleteCard(card: Card) = cards.delete(card)
    override fun checkCard(card: Card, checkCardDto: CheckCardDto): TestResultDto {
        val isFromKey: Boolean = when (checkCardDto.mode) {
            ETestingType.TO_KEY -> false
            ETestingType.FROM_KEY -> true
            else -> throw Exception("bad type")
        }
        val userValue = checkCardDto.userValue!!
        var cardKey: String = card.cardKey!!
        var isAnswerToOtherCard: Boolean = false

        // translateToNative -> isFromKey == true
        val isCorrect: Boolean = if (!isFromKey) {
            card.cardValue!!.contains(userValue)
        } else {
            if (cardKey.contains(userValue)) true else {
                // We need check out in the other cards
                val cards = findListByCardStockId(card.cardStockId!!)

                val cardValues = card.cardValue!!.removePrefix("[").removeSuffix("]")
                    .split(',').map { it.trim() }.toMutableList()

                val cardsContainingUserValue = cards.filter { it.cardKey!!.contains(userValue) }
                val newCard: Card? = cardsContainingUserValue.find { newCard ->
                    val cardValuesByUserValue = newCard.cardValue!!.removePrefix("[").removeSuffix("]")
                        .split(',').map { it.trim() }

                    cardValues.retainAll(cardValuesByUserValue)
                    cardValues.isNotEmpty()
                }

                if (newCard != null) {
                    cardKey = newCard.cardKey!!
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
            isRightAnswer = isCorrect,
            isAnswerToOtherCard = isAnswerToOtherCard,
            cardKey = cardKey
        )
    }


}
