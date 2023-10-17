package com.example.memorizing.card

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface CardApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}/card/{cardId}"],
        produces = ["application/json"]
    )
    fun getCardById(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int,
        @PathVariable("cardId") cardId: Int
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addCardToSetOfCard(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int,
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}/card/{cardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCard(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int,
        @PathVariable("cardId") cardId: Int,
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}/card/{cardId}"],
        produces = ["application/json"]
    )
    fun deleteCard(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int,
        @PathVariable("cardId") cardId: Int,
    ): ResponseEntity<Void>
}