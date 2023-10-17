package com.example.memorizing.storage

import com.example.memorizing.cardStock.CardStock

data class StorageFieldsDto(
    var userId: Int? = null,
    var cardStocks: MutableList<CardStock>? = null
)
