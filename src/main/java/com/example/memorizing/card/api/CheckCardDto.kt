package com.example.memorizing.card.api

data class CheckCardDto(
    var cardStockId: Int? = null,
    var userValue: String? = null,
    var fromKey: Boolean? = null,
    var maxPoint: Int? = null,
)
