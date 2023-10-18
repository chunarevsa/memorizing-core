package com.example.memorizing.card

import com.example.memorizing.cardStock.CardStock
import org.springframework.stereotype.Service
import java.util.*

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
    override fun checkCard(card: Card, checkCardDto: CheckCardDto, cardStock: CardStock): TestResultDto {

        val isFromKey: Boolean = when (checkCardDto.mode) {
            EModeType.TESTING_TO_KEY -> false
            EModeType.TESTING_FROM_KEY -> true
            else -> throw Exception("bad type")
        }
        val userValue = checkCardDto.userValue!!
        var cardKey: String = card.key9!!
        var isAnswerToOtherCard: Boolean = false

        // translateToNative -> isFromKey == true
        val isCorrect: Boolean = if (isFromKey) {
            card.value9!!.contains(userValue)
        } else {
            if (cardKey.contains(userValue)) true else {
                // We need check out in the other cards
                val cards = findListByCardStockId(card.cardStockId!!)

                val cardValues = card.value9!!.removePrefix("[").removeSuffix("]")
                    .split(',').map { it.trim() }.toMutableList()

                val cardsContainingUserValue = cards.filter { it.key9!!.contains(userValue) }
                val newCard: Card? = cardsContainingUserValue.find { newCard ->
                    val cardValuesByUserValue = newCard.value9!!.removePrefix("[").removeSuffix("]")
                        .split(',').map { it.trim() }

                    cardValues.retainAll(cardValuesByUserValue)
                    cardValues.isNotEmpty()
                }

                if (newCard != null) {
                    cardKey = newCard.key9!!
                    isAnswerToOtherCard = true
                    true
                } else false
            }
        }

        if (isCorrect) {
            card.increasePoint(isFromKey, cardStock.maxPoint!!)
        } else card.decreasePoint(isFromKey)


        return TestResultDto(
            isRightAnswer = isCorrect,
            isAnswerToOtherCard = isAnswerToOtherCard,
            cardKey = cardKey
        )
    }


}
