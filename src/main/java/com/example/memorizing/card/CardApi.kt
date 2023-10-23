package com.example.memorizing.card

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface CardApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/card/{cardId}"],
        produces = ["application/json"]
    )
    fun getCardById(
        @PathVariable("cardId") cardId: Int
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/cards"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getCardsByCardStockId(
        @RequestBody cardStockId: Int
    ): ResponseEntity<List<CardDto>>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createCard(
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/{cardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCard(
        @PathVariable("cardId") cardId: Int,
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/card/{cardId}"],
        produces = ["application/json"]
    )
    fun deleteCard(
        @PathVariable("cardId") cardId: Int,
    ): ResponseEntity<Void>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/{cardId}/check"],
        produces = ["application/json"]
    )
    fun checkCard(
        @PathVariable("cardId") cardId: Int,
        @RequestBody checkCardDto: CheckCardDto,
    ): ResponseEntity<TestResultDto>

}