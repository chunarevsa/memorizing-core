package com.example.memorizing.cardStock

import com.example.memorizing.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CardStockServiceImpl(
    private val cardStocks: CardStockRepository,
) : CardStockService {

    companion object {
        const val ENTITY_NAME = "cardStock"
    }

    override fun findById(cardStockId: Int): CardStock =
        cardStocks.findById(cardStockId).orElseThrow { NotFoundException(ENTITY_NAME, "cardStockId", cardStockId) }

    override fun findAllByStorageId(storageId: Int) = cardStocks.findAllByStorageId(storageId)

    override fun create(fields: CardStockFieldsDto): CardStock {
        val cardStock: CardStock = CardStockMapper.fromFields(fields)
        return save(cardStock)
    }

    override fun update(cardStockId: Int, fields: CardStockFieldsDto): CardStock {
        val cardStock: CardStock = CardStockMapper.fromFields(fields, findById(cardStockId))
        return save(cardStock)
    }

    override fun delete(cardStock: CardStock) = cardStocks.delete(cardStock)
    override fun deleteById(cardStockId: Int) = cardStocks.deleteById(cardStockId)

    private fun save(cardStock: CardStock) = cardStocks.save(cardStock)

}