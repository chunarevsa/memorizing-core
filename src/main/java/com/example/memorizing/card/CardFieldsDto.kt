package com.example.memorizing.card

data class CardFieldsDto(
    var cardStockId: Int? = null,
    var key: String? = null,
    var value: String? = null,
    var statusFromKey: ECardStatus? = null,
    var statusToKey: ECardStatus? = null,
)
