package com.example.memorizing.cardStock

import com.example.memorizing.card.Card

data class CardStockDto(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean? = null,
    var onlyFromKey: Boolean? = null,
    val cards: MutableList<Card> = mutableListOf()
)
