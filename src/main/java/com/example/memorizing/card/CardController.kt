package com.example.memorizing.card

import com.example.memorizing.storage.StorageService
import com.example.memorizing.cardStock.CardStockService
import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/storage/{storageId}/cardStock/{cardStockId}")
class CardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val storageService: StorageService,
    private val cardStockService: CardStockService,
    private val cardService: CardService
) : CardApi {

    companion object {
        const val ENTITY_NAME = "card"
    }

    override fun getCardById(
        storageId: Int,
        cardStockId: Int,
        cardId: Int
    ): ResponseEntity<CardDto> {
        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (cardStock.storageId != storage.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!cardStock.cards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = CardDto(
            id = card.id,
            key = card.key9,
            value = card.value9,
            pointFromKey = card.pointFromKey,
            pointToKey = card.pointToKey,
            statusFromKey = card.statusFromKey,
            statusToKey = card.statusToKey
        )

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addCardToCardStock(
        storageId: Int,
        cardStockId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val card = cardService.addCardToCardStock(cardStockId, cardFieldsDto)

        cardStock.cards.add(card)
        cardStockService.saveCardStock(cardStock)

        val result = CardDto(
            id = card.id,
            key = card.key9,
            value = card.value9,
            pointFromKey = card.pointFromKey,
            pointToKey = card.pointToKey,
            statusFromKey = card.statusFromKey,
            statusToKey = card.statusToKey
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        headers.location =
            UriComponentsBuilder.newInstance().path("/storage/{$storageId}/cardStock/{$cardStockId}/card/{id}")
                .buildAndExpand(card.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCard(
        storageId: Int,
        cardStockId: Int,
        cardId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (cardStock.storageId != storage.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!cardStock.cards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        cardService.saveCard(card.apply {
            this.cardStockId = cardStockId
            this.key9 = cardFieldsDto.key
            this.value9 = cardFieldsDto.value
        })

        val result = CardDto(
            id = card.id,
            key = card.key9,
            value = card.value9,
            pointFromKey = card.pointFromKey,
            pointToKey = card.pointToKey,
            statusFromKey = card.statusFromKey,
            statusToKey = card.statusToKey
        )

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteCard(
        storageId: Int,
        cardStockId: Int,
        cardId: Int
    ): ResponseEntity<Void> {
        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val cardStock = cardStockService.findCardStockById(cardStockId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (cardStock.storageId != storage.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!cardStock.cards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        cardService.deleteCard(card)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}