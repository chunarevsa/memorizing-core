package com.example.memorizing.card

import com.example.memorizing.cardStock.CardStockService
import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
//@RequestMapping("/storage/{storageId}/cardStock/{cardStockId}")
@RequestMapping()
class CardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val cardService: CardService
) : CardApi {

    companion object {
        const val ENTITY_NAME = "card"
    }

    override fun getCardById(
        cardId: Int
    ): ResponseEntity<CardDto> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = CardDto(
            id = card.id,
            key = card.cardKey,
            value = card.cardValue,
            pointFromKey = card.pointFromKey,
            pointToKey = card.pointToKey,
            statusFromKey = card.statusFromKey,
            statusToKey = card.statusToKey
        )

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addCardToCardStock(
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val card = cardService.addCardToCardStock(cardFieldsDto)

        val result = CardDto(
            id = card.id,
            key = card.cardKey,
            value = card.cardValue,
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
            UriComponentsBuilder.newInstance().path("/card/{id}")
                .buildAndExpand(card.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCard(
        cardId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardService.saveCard(card.apply {
            this.cardStockId = cardFieldsDto.cardStockId
            this.cardKey = cardFieldsDto.key
            this.cardValue = cardFieldsDto.value
        })

        val result = CardDto(
            id = card.id,
            key = card.cardKey,
            value = card.cardValue,
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
        cardId: Int
    ): ResponseEntity<Void> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardService.deleteCard(card)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

    override fun checkCard(
        cardId: Int,
        checkCardDto: CheckCardDto
    ): ResponseEntity<TestResultDto> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        if (checkCardDto.mode != EModeType.TESTING_TO_KEY || checkCardDto.mode != EModeType.TESTING_TO_KEY)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = cardService.checkCard(card, checkCardDto)

        return ResponseEntity(result, HttpStatus.OK)
    }

}