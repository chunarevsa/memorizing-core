package com.example.memorizing.card.rest.api

data class CardFieldsDto(
    var cardStockId: Int? = null,
    var cardKey: String? = null,
    var cardValue: String? = null,
    var onlyFromKey: Boolean? = null
)