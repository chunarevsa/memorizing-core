package com.example.memorizing.setOfCard

import com.example.memorizing.card.Card
import com.example.memorizing.entity.ELanguage

data class SetOfCardDto(
    var id: Int? = null,
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int? = null,
    var rootOfSetId: Int? = null,
    var listOfCards: MutableList<Card>? = null
)
