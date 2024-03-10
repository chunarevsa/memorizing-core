package com.memorizing.core.card.rest.api

import com.memorizing.commonapi.model.CheckCardDto
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
    fun getCardById(@PathVariable("cardId") cardId: Int): CardDto

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/getAllByCardStockId"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getCardsByCardStockId(@RequestBody fields: CardFieldsDto): List<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/create"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createCard(@RequestBody fields: CardFieldsDto): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/{cardId}/update"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCard(@PathVariable("cardId") cardId: Int, @RequestBody fields: CardFieldsDto): ResponseEntity<CardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/{cardId}/delete"],
        produces = ["application/json"]
    )
    fun deleteCard(@PathVariable("cardId") cardId: Int): ResponseEntity<Void>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/card/{cardId}/check"],
        produces = ["application/json"]
    )
    fun checkCard(@PathVariable("cardId") cardId: Int, @RequestBody checkCardDto: CheckCardDto): TestResultDto

}