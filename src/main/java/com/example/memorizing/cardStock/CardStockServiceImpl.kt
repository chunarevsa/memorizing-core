package com.example.memorizing.cardStock

import com.example.memorizing.cardStock.rest.CardStockController.Companion.ENTITY_NAME
import com.example.memorizing.cardStock.rest.api.CardStockFieldsDto
import com.example.memorizing.cardStock.rest.api.CardStockMapper
import com.example.memorizing.exception.NotFoundException
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