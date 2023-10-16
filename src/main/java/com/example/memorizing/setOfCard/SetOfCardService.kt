package com.example.memorizing.setOfCard

interface SetOfCardService {

    fun findByRootOfSetId(rootOfSetId: Int): List<SetOfCard>

}
