package com.example.memorizing.cardStock

import org.springframework.transaction.annotation.Transactional

interface CardStockService {

//    @Transactional(readOnly = true)
    fun findCardStockById(cardStockId: Int): CardStock?
//    @Transactional(readOnly = true)
    fun findListCardStockByStorageId(storageId: Int): MutableList<CardStock>

//    @Transactional
    fun addCardStockToStorage(cardStockFieldsDto: CardStockFieldsDto): CardStock

//    @Transactional
    fun saveCardStock(cardStock: CardStock): CardStock

//    @Transactional
    fun deleteCardStock(cardStock: CardStock)


}
