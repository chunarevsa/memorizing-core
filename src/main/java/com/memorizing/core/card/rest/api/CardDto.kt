package com.memorizing.core.card.rest.api

data class CardDto(
    var id: Int? = null,
    var cardStockId: Int? = null,
    var cardKey: String? = null,
    var cardValue: String? = null,
    var pointFromKey: Int? = null,
    var pointToKey: Int? = null,
    var statusFromKey: String? = null,
    var statusToKey: String? = null,
)
