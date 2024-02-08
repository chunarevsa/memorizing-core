package com.example.memorizing.card

import com.example.memorizing.card.rest.CardController.Companion.ENTITY_NAME
import com.example.memorizing.card.rest.api.*
import com.example.memorizing.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cards: CardRepository
) : CardService {

    override fun findById(cardId: Int): Card =
        cards.findById(cardId).orElseThrow { NotFoundException(ENTITY_NAME, "cardId", cardId) }

    override fun findAllByCardStockId(cardStockId: Int) = cards.findAllByCardStockId(cardStockId)
    override fun create(fields: CardFieldsDto): Card = save(CardMapper.fromFields(fields))
    override fun update(cardId: Int, fields: CardFieldsDto): Card =
        save(CardMapper.fromFields(fields, findById(cardId)))

    override fun delete(card: Card) = cards.delete(card)
    override fun deleteById(cardId: Int) = cards.deleteById(cardId)
    override fun checkCard(cardId: Int, checkCardDto: CheckCardDto): TestResultDto {
        val card = findById(cardId)

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
                val cards = findAllByCardStockId(card.cardStockId!!)

                val cardValues =
                    cardValue.removePrefix("[").removeSuffix("]").split(',').map { it.trim() }.toMutableList()

                val cardsContainingUserValue = cards.filter { it.cardKey!!.lowercase().contains(userValue) }
                val newCard: Card? = cardsContainingUserValue.find { newCard ->
                    val cardValuesByUserValue =
                        newCard.cardValue!!.lowercase().removePrefix("[").removeSuffix("]").split(',').map { it.trim() }

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
        save(card)

        return TestResultDto(
            rightAnswer = isCorrect, answerToOtherCard = isAnswerToOtherCard, CardDto(
                id = card.id,
                cardKey = card.cardKey,
                cardValue = card.cardValue,
                pointFromKey = card.pointFromKey,
                pointToKey = card.pointToKey,
                statusFromKey = card.statusFromKey.name,
                statusToKey = card.statusToKey.name
            )
        )
    }

    private fun save(card: Card) = cards.save(card)

}
