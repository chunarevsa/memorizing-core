package com.example.memorizing.cardStock

import com.example.memorizing.storage.StorageService
import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/storage/{storageId}")
class CardStockControllerStock(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val storageService: StorageService,
    private val cardStockService: CardStockService,
) : CardStockApi {

    companion object {
        const val ENTITY_NAME = "cardStock"
    }

    override fun getCardStockById(storageId: Int, cardStockId: Int): ResponseEntity<CardStockDto> {
        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (cardStock.storageId != storage.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.name,
            discription = cardStock.discription,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyForward = cardStock.onlyForward,
            cards = cardStock.cards
        )
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addCardStockToStorage(
        storageId: Int,
        cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto> {
        storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.addCardStockToStorage(storageId, cardStockFieldsDto)

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.name,
            discription = cardStock.discription,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyForward = cardStock.onlyForward,
            cards = cardStock.cards
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString()
            )
        )
        headers.location =
            UriComponentsBuilder.newInstance().path("/storage/{$storageId}/cardStock/{id}").buildAndExpand(cardStock.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCardStock(
        storageId: Int,
        cardStockId: Int,
        cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto> {
        storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardStockService.saveCardStock(cardStock.apply {
            this.name = cardStockFieldsDto.name
            this.discription = cardStockFieldsDto.discription
            this.keyType = cardStockFieldsDto.keyType
            this.valueType = cardStockFieldsDto.valueType
            this.maxPoint = cardStockFieldsDto.maxPoint
            this.testModeIsAvailable = cardStockFieldsDto.testModeIsAvailable
            this.onlyForward = cardStockFieldsDto.onlyForward
        })

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.name,
            discription = cardStock.discription,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyForward = cardStock.onlyForward,
            cards = cardStock.cards
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString()
            )
        )
        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteCardStock(
        storageId: Int,
        cardStockId: Int
    ): ResponseEntity<Void> {
        storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardStockService.deleteCardStock(cardStock)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString()
            )
        )
        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}