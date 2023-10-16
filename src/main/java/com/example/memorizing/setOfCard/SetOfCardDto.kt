package com.example.memorizing.setOfCard

import com.example.memorizing.card.Card

data class SetOfCardDto(
    var maxPoint: Int? = null,
    var rootOfSetId: Int? = null,
    val listOfCards: MutableList<Card>? = null
)
