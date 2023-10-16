package com.example.memorizing.rootOfSet

import com.example.memorizing.setOfCard.SetOfCard

data class RootOfSetDto(
    var id: Int? = null,
    var userId: Int? = null,
    var listSetOfCard: MutableList<SetOfCard>? = null
)
