package com.example.memorizing.cardStock

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface CardStockApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/storage/{storageId}/cardStock/{cardStockId}"],
        produces = ["application/json"]
    )
    fun getCardStockById(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}/cardStock"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addCardStockToStorage(
        @PathVariable("storageId") storageId: Int,
        @RequestBody cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}/cardStock/{cardStockId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateCardStock(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int,
        @RequestBody cardStockFieldsDto: CardStockFieldsDto
    ): ResponseEntity<CardStockDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/storage/{storageId}/cardStock/{cardStockId}"],
        produces = ["application/json"]
    )
    fun deleteCardStock(
        @PathVariable("storageId") storageId: Int,
        @PathVariable("cardStockId") cardStockId: Int
    ): ResponseEntity<Void>

}
