package com.example.memorizing.cardStock.rest.api

data class CardStockDto(
    var id: Int? = null,
    var storageId: Int? = null,
    var cardStockName: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean? = null,
    var onlyFromKey: Boolean? = null,
)
