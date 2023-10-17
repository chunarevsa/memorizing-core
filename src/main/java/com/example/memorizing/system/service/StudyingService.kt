package com.example.memorizing.system.service

import com.example.memorizing.entity.ELanguage
import com.example.memorizing.cardStock.CardStock
import com.example.memorizing.card.CardRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudyingService(
    private val cardRepository: CardRepository,
) {

    fun checkCard(
        setOfCardsId: String,
        wordKey: String,
        userValue: String,
        translateToNative: Boolean,
    ): Boolean {
        val setOfCard = getSetOfCardsById(setOfCardsId)

        var card = setOfCard.cards[wordKey] ?: throw Exception("not found")

        if (userValue.isBlank()) return false
        val isCorrect: Boolean = if (translateToNative) {
            card.translate.contains(userValue)
        } else {
            if (card.value.contains(userValue)) true else {
                // We need check out in the other cards
                val listTranslatesByCard = card.translate.split(',').map { it.trim() }
                val map = setOfCard.cards.keys.filter { it.contains(userValue) }
                val newKeyOfCard: String? = map.find { key ->
                    val card1 = setOfCard.cards[key]
                    val listTranslatesByUserValue = card1?.translate!!.split(',')
                    Collections.disjoint(listTranslatesByCard, listTranslatesByUserValue)
                }

                if (newKeyOfCard != null) {
                    card = setOfCard.cards[newKeyOfCard]!!
                    true
                } else false
            }
        }
        // Why is it correct?
        // [таким образом]:furthe
        // thus

//        [собирать]:asse
//        gather

//        [далее, дальше]:rath
//        further

//        [утвердить, одобрить]:adop
//        approve
        if (isCorrect) {
            card.increasePoint(translateToNative, setOfCard.maxPoint)
        } else card.decreasePoint(translateToNative)
        cardRepository.saveCard(setOfCardsId, card)

        return isCorrect

    }

    fun getCardBySetOfCardsIdAndCardKey(setOfCardsId: String, cardKey: String) =
        getSetOfCardsById(setOfCardsId).cards[cardKey] ?: throw Exception("not found")


    fun getSetOfCardsById(setOfCardsById: String): CardStock {
        return cardRepository.findSetOfCardsById(setOfCardsById)
    }

    fun createSetOfCards(language: ELanguage, nativeLanguage: ELanguage, maxPoint: Int): String {
        val cardStock = CardStock(Pair(language, nativeLanguage))
        cardRepository.saveSetOfCards(cardStock)
        cardRepository.createNewFilesForSet(cardStock.pair!!)
        return cardStock.id
    }


    fun getListOfSetOfCards(rootOfSetIds: MutableSet<String>): List<CardStock> {
        val setOfCardsIds: MutableSet<String> = mutableSetOf()
        val listRootOfSets = rootOfSetIds.map { getRootOfSets(it) }
        listRootOfSets.forEach { it?.setOfCardsIds?.forEach { id -> setOfCardsIds.add(id) } }

        return setOfCardsIds.map { cardRepository.findSetOfCardsById(it) }
    }

}
