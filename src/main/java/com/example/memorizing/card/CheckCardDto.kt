package com.example.memorizing.card

data class CheckCardDto(
    var cardStockId: Int? = null,
    var userValue: String? = null,
    var mode: ETestingType? = null,
    var maxPoint: Int? = null,
)
