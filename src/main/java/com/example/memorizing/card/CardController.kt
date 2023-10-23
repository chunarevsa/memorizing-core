package com.example.memorizing.card

import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping
class CardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val cardService: CardService
) : CardApi {

    companion object {
        const val ENTITY_NAME = "com/example/memorizing/card"
    }

    override fun getCardById(
        cardId: Int
    ): ResponseEntity<CardDto> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = CardDto(
            id = card.id,
            cardKey = card.cardKey,
            cardValue = card.cardValue,
            pointFromKey = card.pointFromKey,
            pointToKey = card.pointToKey,
            statusFromKey = card.statusFromKey,
            statusToKey = card.statusToKey
        )

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun getCardsByCardStockId(cardStockId: Int): ResponseEntity<List<CardDto>> {
        val cards = cardService.findListByCardStockId(cardStockId)

        val result = cards.map {
            CardDto(
                id = it.id,
                cardKey = it.cardKey,
                cardValue = it.cardValue,
                pointFromKey = it.pointFromKey,
                pointToKey = it.pointToKey,
                statusFromKey = it.statusFromKey,
                statusToKey = it.statusToKey
            )
        }
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun createCard(
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val card = cardService.createCard(cardFieldsDto)

        val result = CardDto(
            id = card.id,
            cardKey = card.cardKey,
            cardValue = card.cardValue,
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
            UriComponentsBuilder.newInstance().path("/com/example/memorizing/card/{id}")
                .buildAndExpand(card.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCard(
        cardId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        cardService.saveCard(card.apply {
            cardFieldsDto.cardKey.let { this.cardKey = it }
            cardFieldsDto.cardValue.let { this.cardValue = it }
        })

        val result = CardDto(
            id = card.id,
            cardKey = card.cardKey,
            cardValue = card.cardValue,
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

        if (checkCardDto.mode != ETestingType.TO_KEY || checkCardDto.mode != ETestingType.TO_KEY)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = cardService.checkCard(card, checkCardDto)

        return ResponseEntity(result, HttpStatus.OK)
    }

}