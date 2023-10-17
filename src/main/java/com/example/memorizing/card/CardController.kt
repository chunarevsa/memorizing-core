package com.example.memorizing.card

import com.example.memorizing.rootOfSet.RootOfSetService
import com.example.memorizing.setOfCard.SetOfCardService
import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}")
class CardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val rootOfSetService: RootOfSetService,
    private val setOfCardService: SetOfCardService,
    private val cardService: CardService
) : CardApi {

    companion object {
        const val ENTITY_NAME = "card"
    }

    override fun getCardById(
        rootOfSetId: Int,
        setOfCardId: Int,
        cardId: Int
    ): ResponseEntity<CardDto> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (setOfCard.rootOfSetId != rootOfSet.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!setOfCard.listOfCards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = CardDto().apply {
            this.id = card.id
            this.setOfCardId = card.setOfCardId
            this.value = card.value
            this.translate = card.translate
            this.type = card.type
            this.pointToNative = card.pointToNative
            this.pointFromNative = card.pointFromNative
            this.statusToNative = card.statusToNative
            this.statusFromNative = card.statusFromNative
        }
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addCardToSetOfCard(
        rootOfSetId: Int,
        setOfCardId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val card = cardService.addCardToSetOfCard(setOfCardId, cardFieldsDto)

        setOfCard.listOfCards.add(card)
        setOfCardService.saveSetOfCard(setOfCard)

        val result = CardDto().apply {
            this.id = card.id
            this.setOfCardId = card.setOfCardId
            this.value = card.value
            this.translate = card.translate
            this.type = card.type
            this.pointToNative = card.pointToNative
            this.pointFromNative = card.pointFromNative
            this.statusToNative = card.statusToNative
            this.statusFromNative = card.statusFromNative
        }
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        headers.location =
            UriComponentsBuilder.newInstance().path("/rootOfSet/{$rootOfSetId}/setOfCard/{$setOfCardId}/card/{id}")
                .buildAndExpand(card.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateCard(
        rootOfSetId: Int,
        setOfCardId: Int,
        cardId: Int,
        cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (setOfCard.rootOfSetId != rootOfSet.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!setOfCard.listOfCards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        cardService.saveCard(card.apply {
            this.value = cardFieldsDto.value
            this.translate = cardFieldsDto.translate
            this.type = cardFieldsDto.type
            this.pointToNative = cardFieldsDto.pointToNative
            this.pointFromNative = cardFieldsDto.pointFromNative
            this.statusToNative = cardFieldsDto.statusToNative
            this.statusFromNative = cardFieldsDto.statusFromNative
        })

        val result = CardDto().apply {
            this.id = card.id
            this.setOfCardId = card.setOfCardId
            this.value = card.value
            this.translate = card.translate
            this.type = card.type
            this.pointToNative = card.pointToNative
            this.pointFromNative = card.pointFromNative
            this.statusToNative = card.statusToNative
            this.statusFromNative = card.statusFromNative
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteCard(
        rootOfSetId: Int,
        setOfCardId: Int,
        cardId: Int
    ): ResponseEntity<Void> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (setOfCard.rootOfSetId != rootOfSet.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val card = cardService.findCardById(cardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (!setOfCard.listOfCards.contains(card)) return ResponseEntity(HttpStatus.BAD_REQUEST)

        cardService.deleteCard(card)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, card.id.toString()
            )
        )
        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}