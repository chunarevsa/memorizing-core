package com.example.memorizing.cardStock

import org.springframework.stereotype.Service

@Service
class CardStockServiceImpl(
    private val cardStocks: CardStockRepository,
) : CardStockService {

    override fun findCardStockById(cardStockId: Int): CardStock? = cardStocks.findById(cardStockId)
    override fun findListCardStockByStorageId(storageId: Int) = cardStocks.findAllByStorageId(storageId)

    override fun addCardStockToStorage(storageId: Int, cardStockFieldsDto: CardStockFieldsDto): CardStock {
        return cardStocks.saveCardStock(CardStock().apply {
            this.storageId = storageId
            this.pair = cardStockFieldsDto.pair
            this.maxPoint = cardStockFieldsDto.maxPoint
        })
    }

    override fun saveCardStock(cardStock: CardStock) = cardStocks.saveCardStock(cardStock)
    override fun deleteCardStock(cardStock: CardStock) = cardStocks.deleteCardStock(cardStock)

}