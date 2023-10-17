package com.example.memorizing.storage

import com.example.memorizing.cardStock.CardStock

data class StorageDto(
    var id: Int? = null,
    var userId: Int? = null,
    var cardStocks: MutableList<CardStock>? = null
)
