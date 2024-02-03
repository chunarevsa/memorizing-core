package com.example.memorizing.cardStock

import com.example.memorizing.exception.BadRequestException
import com.example.memorizing.storage.StorageController
import com.example.memorizing.storage.StorageMapper
import com.example.memorizing.util.HeaderUtil
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
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
        val cardStock = cardStockService.findById(cardStockId)

        return CardStockMapper.toCardStockDto(cardStock)
    }

    override fun getCardStocksByStorageId(fields: CardStockFieldsDto): List<CardStockDto> {
        log.debug("getCardStocksByStorageId with req: $fields")
        fields.storageId ?: throw BadRequestException(ENTITY_NAME, "storageId", "null")

        val cardStocks = cardStockService.findAllByStorageId(fields.storageId!!)
        return cardStocks.map { CardStockMapper.toCardStockDto(it) }
    }

    override fun createCardStock(fields: CardStockFieldsDto): ResponseEntity<CardStockDto> {
        log.debug("createCardStock with req: $fields")
        fields.storageId ?: throw BadRequestException(StorageController.ENTITY_NAME, "storageId", "null")
        fields.cardStockName ?: throw BadRequestException(StorageController.ENTITY_NAME, "cardStockName", "null")
        fields.description ?: throw BadRequestException(StorageController.ENTITY_NAME, "description", "null")
        fields.keyType ?: throw BadRequestException(StorageController.ENTITY_NAME, "keyType", "null")
        fields.valueType ?: throw BadRequestException(StorageController.ENTITY_NAME, "valueType", "null")

        val cardStock = cardStockService.create(fields)

        return ResponseEntity(
            CardStockMapper.toCardStockDto(cardStock),
            HeaderUtil.createEntityCreationAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString(), "/${ENTITY_NAME}/${cardStock.id}"
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateCardStock(
        cardStockId: Int,
        fields: CardStockFieldsDto
    ): ResponseEntity<CardStockDto> {
        log.debug("updateCardStock with path variable $cardStockId and req: $fields")
        if (fields.storageId != null) throw BadRequestException("storageId should be null")

        val cardStock = cardStockService.update(cardStockId, fields)

        return ResponseEntity(
            CardStockMapper.toCardStockDto(cardStock),
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false,
                StorageController.ENTITY_NAME, cardStock.id.toString(), "/${ENTITY_NAME}/${cardStock.id}"
            ),
            HttpStatus.NO_CONTENT
        )
    }

    override fun deleteCardStock(cardStockId: Int): ResponseEntity<Void> {
        log.debug("deleteCardStock with path variable $cardStockId ")

        cardStockService.deleteById(cardStockId)

        return ResponseEntity(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, cardStockId.toString(), "/${ENTITY_NAME}/$cardStockId"
            ),
            HttpStatus.NO_CONTENT
        )
    }

}