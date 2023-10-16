package com.example.memorizing.setOfCard

import org.springframework.stereotype.Service

@Service
class SetOfCardServiceImpl(
    private val setOfCards: SetOfCardRepository
) : SetOfCardService {

    override fun findSetOfCardById(setOfCardId: Int): SetOfCard? = setOfCards.findById(setOfCardId)
    override fun findListSetOfCardByRootOfSetId(rootOfSetId: Int) = setOfCards.findAllByRootOfSetId(rootOfSetId)

    override fun create(rootOfSetId: Int): SetOfCard? {
        if (setOfCards.existsByRootOfSetId(rootOfSetId)) return null
        return setOfCards.saveSetOfCard(SetOfCard(rootOfSetId))
    }

    override fun saveSetOfCard(setOfCard: SetOfCard) = setOfCards.saveSetOfCard(setOfCard)
    override fun deleteSetOfCard(setOfCard: SetOfCard) = setOfCards.delete(setOfCard)

}