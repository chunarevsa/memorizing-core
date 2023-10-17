package com.example.memorizing.card

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface CardApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/storage/{storageId}/cardStock/{cardStockId}/card/{cardId}"],
        produces = ["application/json"]
    )
    fun getCardById(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @PathVariable("cardId") cardId: Int
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}/cardStock/{cardStockId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addCardToCardStock(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}/cardStock/{cardStockId}/card/{cardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCard(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @PathVariable("cardId") cardId: Int,
        @RequestBody cardFieldsDto: CardFieldsDto
    ): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/storage/{storageId}/cardStock/{cardStockId}/card/{cardId}"],
        produces = ["application/json"]
    )
    fun deleteCard(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @PathVariable("cardId") cardId: Int,
    ): ResponseEntity<Void>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}/cardStock/{cardStockId}/card/{cardId}"],
        produces = ["application/json"]
    )
    fun checkCard(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @PathVariable("cardId") cardId: Int,
        @RequestBody checkCardDto: CheckCardDto,
    ): ResponseEntity<TestResultDto>

}