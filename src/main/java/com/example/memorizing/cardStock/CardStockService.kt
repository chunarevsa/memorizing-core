package com.example.memorizing.cardStock

interface CardStockService {

//    @Transactional(readOnly = true)
    fun findCardStockById(cardStockId: Int): CardStock?
//    @Transactional(readOnly = true)
    fun findAllCardStockByStorageId(storageId: Int): MutableList<CardStock>

//    @Transactional
    fun createCardStock(cardStockFieldsDto: CardStockFieldsDto): CardStock

//    @Transactional
    fun saveCardStock(cardStock: CardStock): CardStock

//    @Transactional
    fun deleteCardStock(cardStock: CardStock)


}
