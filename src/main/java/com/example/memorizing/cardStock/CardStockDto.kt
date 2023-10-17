package com.example.memorizing.cardStock

import com.example.memorizing.card.Card
import com.example.memorizing.entity.ELanguage

data class CardStockDto(
    var id: Int? = null,
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int? = null,
    var storageId: Int? = null,
    var cards: MutableList<Card>? = null
)
