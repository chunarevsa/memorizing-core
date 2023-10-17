package com.example.memorizing.cardStock

data class CardStockFieldsDto(
    var name: String? = null,
    var discription: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean? = null,
    var onlyForward: Boolean? = null
)
