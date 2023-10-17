package com.example.memorizing.card

data class CardDto(
    var id: Int? = null,
    var key: String? = null,
    var value: String? = null,
    var pointFromKey: Int? = null,
    var pointToKey: Int? = null,
    var statusFromKey: ECardStatus? = null,
    var statusToKey: ECardStatus? = null,
)
