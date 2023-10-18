package com.example.memorizing.service

import com.example.memorizing.entity.ELanguage
import com.example.memorizing.entity.RootOfSets
import com.example.memorizing.entity.SetOfCards
import com.example.memorizing.repository.CardRepository
import com.example.memorizing.repository.RootOfSetsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val rootOfSetsRepository: RootOfSetsRepository,
) {

    fun checkCard(
        setOfCardsId: String,
        wordKey: String,
        userValue: String,
        translateToNative: Boolean,
    ): Boolean {
        val setOfCard = getSetOfCardsById(setOfCardsId)

        var card = setOfCard.mapOfCards[wordKey] ?: throw Exception("not found")

        if (userValue.isBlank()) return false
        val isCorrect: Boolean = if (translateToNative) {
            card.translate.contains(userValue)
        } else {
            if (card.value.contains(userValue)) true else {
                // We need check out in the other cards
                val listTranslatesByCard = card.translate.removePrefix("[").removeSuffix("]")
                    .split(',').map { it.trim() }.toMutableList()
                val map = setOfCard.mapOfCards.keys.filter { it.contains(userValue) }
                val newKeyOfCard: String? = map.find { key ->
                    val card1 = setOfCard.mapOfCards[key]
                    val listTranslatesByUserValue = card1?.translate!!.removePrefix("[").removeSuffix("]")
                        .split(',')

                    listTranslatesByCard.retainAll(listTranslatesByUserValue)
                    listTranslatesByCard.isNotEmpty()
                }

                if (newKeyOfCard != null) {
                    card = setOfCard.mapOfCards[newKeyOfCard]!!
                    true
                } else false
            }
        }

        if (isCorrect) card.increasePoint(translateToNative, setOfCard.maxPoint) else card.decreasePoint(translateToNative)
        cardRepository.saveCard(setOfCardsId, card)

        return isCorrect

    }

    fun getCardBySetOfCardsIdAndCardKey(setOfCardsId: String, cardKey: String) =
        getSetOfCardsById(setOfCardsId).mapOfCards[cardKey] ?: throw Exception("not found")


    fun getSetOfCardsById(setOfCardsById: String): SetOfCards {
        return cardRepository.findSetOfCardsById(setOfCardsById)
    }

    fun createRootOfSet(username: String): String {
        val rootOfSets = RootOfSets(username)
        rootOfSetsRepository.saveRootOfSets(rootOfSets)
        return rootOfSets.id
    }

    fun getRootOfSets(rootId: String): RootOfSets? {
        return rootOfSetsRepository.findRootOfSets(rootId)
    }

    fun createSetOfCards(language: ELanguage, nativeLanguage: ELanguage, maxPoint: Int): String {
        val setOfCards = SetOfCards(Pair(language, nativeLanguage))
        cardRepository.saveSetOfCards(setOfCards)
        cardRepository.createNewFilesForSet(setOfCards.pair!!)
        return setOfCards.id
    }

    fun saveRootOfSets(rootOfSets: RootOfSets) {
        rootOfSetsRepository.saveRootOfSets(rootOfSets)
    }

    fun getListOfSetOfCards(rootOfSetIds: MutableSet<String>): List<SetOfCards> {
        val setOfCardsIds: MutableSet<String> = mutableSetOf()
        val listRootOfSets = rootOfSetIds.map { getRootOfSets(it) }
        listRootOfSets.forEach { it?.setOfCardsIds?.forEach { id -> setOfCardsIds.add(id) } }

        return setOfCardsIds.map { cardRepository.findSetOfCardsById(it) }
    }


}
