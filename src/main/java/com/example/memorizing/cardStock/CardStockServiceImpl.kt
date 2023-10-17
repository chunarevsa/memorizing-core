package com.example.memorizing.cardStock

import org.springframework.stereotype.Service

@Service
class CardStockServiceImpl(
    private val cardStocks: CardStockRepository,
) : CardStockService {

    override fun findCardStockById(cardStockId: Int): CardStock? = cardStocks.findById(cardStockId)
    override fun findListCardStockByStorageId(storageId: Int) = cardStocks.findAllByStorageId(storageId)

    override fun addCardStockToStorage(storageId: Int, cardStockFieldsDto: CardStockFieldsDto): CardStock {
        return cardStocks.saveCardStock(CardStock(
            name = cardStockFieldsDto.name,
            discription = cardStockFieldsDto.discription,
            keyType = cardStockFieldsDto.keyType,
            valueType = cardStockFieldsDto.valueType,
            maxPoint = cardStockFieldsDto.maxPoint,
            testModeIsAvailable = cardStockFieldsDto.testModeIsAvailable,
            onlyForward = cardStockFieldsDto.onlyForward
        ))
    }

    override fun saveCardStock(cardStock: CardStock) = cardStocks.saveCardStock(cardStock)
    override fun deleteCardStock(cardStock: CardStock) = cardStocks.deleteCardStock(cardStock)

}