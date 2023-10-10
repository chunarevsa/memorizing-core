package com.example.memorizing.service

import com.example.memorizing.entity.Card
import com.example.memorizing.entity.ELanguage
import com.example.memorizing.entity.RootOfSets
import com.example.memorizing.entity.SetOfCards
import com.example.memorizing.repository.CardRepository
import com.example.memorizing.repository.RootOfSetsRepository
import org.springframework.stereotype.Service

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val rootOfSetsRepository: RootOfSetsRepository,
) {

    fun checkCard(
        card: Card,
        setOfCardsById: String,
        userValue: String,
        translateToNative: Boolean,
        userMaxPoint: Int
    ): Boolean {
        val isCorrect: Boolean =
            if (translateToNative) card.translate.contains(userValue) else card.value.contains(userValue)
        if (isCorrect) card.increasePoint(translateToNative, userMaxPoint) else card.decreasePoint(translateToNative)
        cardRepository.saveCard(setOfCardsById, card)

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

    fun createSetOfCards(language: ELanguage, nativeLanguage: ELanguage): String {
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
