package com.example.memorizing.cardStock

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface CardStockApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/cardStock/{cardStockId}"],
        produces = ["application/json"]
    )
    fun getCardStockById(
        @PathVariable("cardStockId") cardStockId: Int
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/cardStock"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createCardStock(
        @RequestBody cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/cardStock/{cardStockId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCardStock(
        @PathVariable("cardStockId") cardStockId: Int,
        @RequestBody cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/cardStock/{cardStockId}"],
        produces = ["application/json"]
    )
    fun deleteCardStock(
        @PathVariable("cardStockId") cardStockId: Int
    ): ResponseEntity<Void>

}
