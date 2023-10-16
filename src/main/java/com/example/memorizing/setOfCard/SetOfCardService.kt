package com.example.memorizing.setOfCard

import org.springframework.transaction.annotation.Transactional

interface SetOfCardService {

    @Transactional(readOnly = true)
    fun findSetOfCardById(setOfCardId: Int): SetOfCard?
    @Transactional(readOnly = true)
    fun findListSetOfCardByRootOfSetId(rootOfSetId: Int): MutableList<SetOfCard>

    @Transactional
    fun create(rootOfSetId: Int): SetOfCard?

    @Transactional
    fun saveSetOfCard(setOfCard: SetOfCard): SetOfCard

    @Transactional
    fun deleteSetOfCard(setOfCard: SetOfCard)


}
