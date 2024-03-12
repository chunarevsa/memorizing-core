package com.memorizing.core.cardStock.rest

import com.memorizing.core.cardStock.CardStockService
import com.memorizing.core.cardStock.rest.api.CardStockApi
import com.memorizing.core.cardStock.rest.api.CardStockDto
import com.memorizing.core.cardStock.rest.api.CardStockFieldsDto
import com.memorizing.core.cardStock.rest.api.CardStockMapper
import com.memorizing.core.exception.BadRequestException
import com.memorizing.core.util.HeaderUtil
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class CardStockController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val cardStockService: CardStockService,
) : CardStockApi {
    private val log = Logger.getLogger(CardStockController::class.java)

    companion object {
        const val ENTITY_NAME = "cardStock"
    }

    override fun getCardStockById(cardStockId: Int): CardStockDto {
        log.debug("getCardStockById with req: storageId = $cardStockId")
        return CardStockMapper.toCardStockDto(cardStockService.findById(cardStockId))
    }

    override fun getCardStocksByStorageId(fields: CardStockFieldsDto): List<CardStockDto> {
        log.debug("getCardStocksByStorageId with req: $fields")
        fields.storageId ?: throw BadRequestException(ENTITY_NAME, "storageId", "null")

        val cardStocks = cardStockService.findAllByStorageId(fields.storageId!!)
        return cardStocks.map { CardStockMapper.toCardStockDto(it) }
    }

    override fun createCardStock(fields: CardStockFieldsDto): ResponseEntity<CardStockDto> {
        log.debug("createCardStock with req: $fields")
        fields.storageId ?: throw BadRequestException(ENTITY_NAME, "storageId", "null")
        if (fields.cardStockName.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "cardStockName", "null")
        if (fields.description.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "description", "null")
        if (fields.keyType.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "keyType", "null")
        if (fields.valueType.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "valueType", "null")

        val cardStock = cardStockService.create(fields)

        return ResponseEntity(
            CardStockMapper.toCardStockDto(cardStock),
            HeaderUtil.createEntityCreationAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString(), "/$ENTITY_NAME/${cardStock.id}"
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateCardStock(cardStockId: Int, fields: CardStockFieldsDto): ResponseEntity<CardStockDto> {
        log.debug("updateCardStock with path variable $cardStockId and req: $fields")
        if (fields.storageId != null) throw BadRequestException("storageId should be null")

        val cardStock = cardStockService.update(cardStockId, fields)

        return ResponseEntity(
            CardStockMapper.toCardStockDto(cardStock),
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString(), "/$ENTITY_NAME/${cardStock.id}"
            ),
            HttpStatus.NO_CONTENT
        )
    }

    override fun deleteCardStock(cardStockId: Int): ResponseEntity<Void> {
        log.debug("deleteCardStock with path variable $cardStockId ")

        cardStockService.deleteById(cardStockId)

        return ResponseEntity(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, cardStockId.toString(), "/$ENTITY_NAME/$cardStockId"
            ),
            HttpStatus.NO_CONTENT
        )
    }

}