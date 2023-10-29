package com.example.memorizing.cardStock

data class CardStockFieldsDto(
    var storageId: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean? = null,
    var onlyFromKey: Boolean? = null
)
