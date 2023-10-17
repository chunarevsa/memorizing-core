package com.example.memorizing.setOfCard

import org.springframework.stereotype.Service

@Service
class SetOfCardServiceImpl(
    private val setOfCards: SetOfCardRepository,
) : SetOfCardService {

    override fun findSetOfCardById(setOfCardId: Int): SetOfCard? = setOfCards.findById(setOfCardId)
    override fun findListSetOfCardByRootOfSetId(rootOfSetId: Int) = setOfCards.findAllByRootOfSetId(rootOfSetId)

    override fun createSetOfCard(rootOfSetId: Int, setOfCardFieldsDto: SetOfCardFieldsDto): SetOfCard {
        return setOfCards.saveSetOfCard(SetOfCard().apply {
            this.rootOfSetId = rootOfSetId
            this.pair = setOfCardFieldsDto.pair
            this.maxPoint = setOfCardFieldsDto.maxPoint
        })
    }

    override fun saveSetOfCard(setOfCard: SetOfCard) = setOfCards.saveSetOfCard(setOfCard)
    override fun deleteSetOfCard(setOfCard: SetOfCard) = setOfCards.delete(setOfCard)

}