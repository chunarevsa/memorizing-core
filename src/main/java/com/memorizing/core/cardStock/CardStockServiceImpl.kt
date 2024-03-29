package com.memorizing.core.cardStock

import com.memorizing.core.cardStock.rest.CardStockController.Companion.ENTITY_NAME
import com.memorizing.core.cardStock.rest.api.CardStockFieldsDto
import com.memorizing.core.cardStock.rest.api.CardStockMapper
import com.memorizing.core.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CardStockServiceImpl(
    private val cardStocks: CardStockRepository,
) : CardStockService {

    override fun findById(cardStockId: Int): CardStock =
        cardStocks.findById(cardStockId).orElseThrow { NotFoundException(ENTITY_NAME, "cardStockId", cardStockId) }

    override fun findAllByStorageId(storageId: Int) = cardStocks.findAllByStorageId(storageId)
    override fun create(fields: CardStockFieldsDto): CardStock = save(CardStockMapper.fromFields(fields))
    override fun update(cardStockId: Int, fields: CardStockFieldsDto): CardStock =
        save(CardStockMapper.fromFields(fields, findById(cardStockId)))

    override fun delete(cardStock: CardStock) = cardStocks.delete(cardStock)
    override fun deleteById(cardStockId: Int) = cardStocks.deleteById(cardStockId)
    private fun save(cardStock: CardStock) = cardStocks.save(cardStock)

}