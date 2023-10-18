package com.example.memorizing.cardStock

import org.springframework.stereotype.Service

@Service
class CardStockServiceImpl(
    private val cardStocks: CardStockRepository,
) : CardStockService {

    override fun findCardStockById(cardStockId: Int): CardStock? = cardStocks.findById(cardStockId)
    override fun findListCardStockByStorageId(storageId: Int) = cardStocks.findAllByStorageId(storageId)

    override fun createCardStock(cardStockFieldsDto: CardStockFieldsDto): CardStock {
        return cardStocks.save(
            CardStock(
                storageId = cardStockFieldsDto.storageId,
                cardStockName = cardStockFieldsDto.cardStockName,
                description = cardStockFieldsDto.description,
                keyType = cardStockFieldsDto.keyType,
                valueType = cardStockFieldsDto.valueType,
                maxPoint = cardStockFieldsDto.maxPoint,
                testModeIsAvailable = cardStockFieldsDto.testModeIsAvailable!!,
                onlyFromKey = cardStockFieldsDto.onlyFromKey!!
            )
        )
    }

    override fun saveCardStock(cardStock: CardStock) = cardStocks.save(cardStock)
    override fun deleteCardStock(cardStock: CardStock) = cardStocks.delete(cardStock)

}