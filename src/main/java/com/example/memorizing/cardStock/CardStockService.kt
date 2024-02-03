package com.example.memorizing.cardStock

interface CardStockService {
    fun findById(cardStockId: Int): CardStock
    fun findAllByStorageId(storageId: Int): List<CardStock>
    fun create(fields: CardStockFieldsDto): CardStock
    fun update(cardStockId: Int, fields: CardStockFieldsDto): CardStock
    fun delete(cardStock: CardStock)
    fun deleteById(cardStockId: Int)

}
