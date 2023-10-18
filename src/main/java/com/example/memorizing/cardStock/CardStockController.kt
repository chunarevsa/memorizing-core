package com.example.memorizing.cardStock

import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping
class CardStockController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val cardStockService: CardStockService,
) : CardStockApi {

    companion object {
        const val ENTITY_NAME = "cardStock"
    }

    override fun getCardStockById(cardStockId: Int): ResponseEntity<CardStockDto> {
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.cardStockName,
            description = cardStock.description,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyFromKey = cardStock.onlyFromKey
        )
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addCardStockToStorage(cardStockFieldsDto: CardStockFieldsDto): ResponseEntity<CardStockDto> {
        val cardStock = cardStockService.addCardStockToStorage(cardStockFieldsDto)

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.cardStockName,
            description = cardStock.description,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyFromKey = cardStock.onlyFromKey,
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString()
            )
        )
        headers.location =
            UriComponentsBuilder.newInstance().path("/cardStock/{id}").buildAndExpand(cardStock.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCardStock(
        cardStockId: Int,
        cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto> {
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardStockService.saveCardStock(cardStock.apply {
            this.cardStockName = cardStockFieldsDto.cardStockName
            this.description = cardStockFieldsDto.description
            this.keyType = cardStockFieldsDto.keyType
            this.valueType = cardStockFieldsDto.valueType
            this.maxPoint = cardStockFieldsDto.maxPoint
            this.testModeIsAvailable = cardStockFieldsDto.testModeIsAvailable!!
            this.onlyFromKey = cardStockFieldsDto.onlyFromKey!!
        })

        val result = CardStockDto(
            id = cardStock.id,
            name = cardStock.cardStockName,
            description = cardStock.description,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyFromKey = cardStock.onlyFromKey,
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, cardStock.id.toString()
            )
        )
        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteCardStock(cardStockId: Int): ResponseEntity<Void> {
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