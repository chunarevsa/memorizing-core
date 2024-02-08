package com.example.memorizing.card.rest

import com.example.memorizing.card.CardService
import com.example.memorizing.card.rest.*
import com.example.memorizing.card.rest.api.*
import com.example.memorizing.exception.BadRequestException
import com.example.memorizing.util.HeaderUtil
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class CardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val cardService: CardService
) : CardApi {
    private val log = Logger.getLogger(CardController::class.java)

    companion object {
        const val ENTITY_NAME = "card"
    }

    override fun getCardById(cardId: Int): CardDto {
        log.debug("getCardById with req: storageId = $cardId")
        return CardMapper.toCardDto(cardService.findById(cardId))
    }

    override fun getCardsByCardStockId(fields: CardFieldsDto): List<CardDto> {
        log.debug("getCardsByCardStockId with req: $fields")
        fields.cardStockId ?: throw BadRequestException(ENTITY_NAME, "cardStockId", "null")

        val cards = cardService.findAllByCardStockId(fields.cardStockId!!)
        return cards.map { CardMapper.toCardDto(it) }
    }

    override fun createCard(fields: CardFieldsDto): ResponseEntity<CardDto> {
        log.debug("createCard with req: $fields")
        fields.cardStockId ?: throw BadRequestException(ENTITY_NAME, "cardStockId", "null")
        if (fields.cardKey.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "cardKey", "null")
        if (fields.cardValue.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "cardKey", "null")
        fields.onlyFromKey ?: throw BadRequestException(ENTITY_NAME, "onlyFromKey ", "null")

        val card = cardService.create(fields)

        return ResponseEntity(
            CardMapper.toCardDto(card),
            HeaderUtil.createEntityCreationAlert(
                applicationName, false,
                ENTITY_NAME, card.id.toString(), "/$ENTITY_NAME/${card.id}"
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateCard(cardId: Int, fields: CardFieldsDto): ResponseEntity<CardDto> {
        log.debug("updateCard with path variable $cardId and req: $fields")
        if (fields.cardKey != null) throw BadRequestException("cardKey should be null")
        if (fields.cardStockId != null) throw BadRequestException("cardStockId should be null")
        if (fields.cardValue.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "cardKey", "null")

        val card = cardService.update(cardId, fields)

        return ResponseEntity(
            CardMapper.toCardDto(card),
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, card.id.toString(), "/$ENTITY_NAME/${card.id}"
            ),
            HttpStatus.NO_CONTENT
        )
    }

    override fun deleteCard(cardId: Int): ResponseEntity<Void> {
        log.debug("deleteCard with path variable $cardId")

        cardService.deleteById(cardId)

        return ResponseEntity(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, cardId.toString(), "/$ENTITY_NAME/$cardId"
            ),
            HttpStatus.NO_CONTENT
        )
    }

    override fun checkCard(cardId: Int, checkCardDto: CheckCardDto): TestResultDto {
        log.debug("checkCard with path variable $cardId and req: $checkCardDto")
        checkCardDto.cardStockId ?: throw BadRequestException(ENTITY_NAME, "cardStockId", "null")
        if (checkCardDto.userValue.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "userValue", "null")
        checkCardDto.fromKey ?: throw BadRequestException(ENTITY_NAME, "fromKey", "null")
        checkCardDto.maxPoint ?: throw BadRequestException(ENTITY_NAME, "maxPoint", "null")

        return cardService.checkCard(cardId, checkCardDto)
    }

}