package com.example.memorizing.rootOfSet

import com.example.memorizing.setOfCard.SetOfCard

data class RootOfSetFieldsDto(
    var userId: Int? = null,
    var listSetOfCard: MutableList<SetOfCard>? = null
)
