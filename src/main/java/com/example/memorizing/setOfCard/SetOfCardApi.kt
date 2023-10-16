package com.example.memorizing.setOfCard

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface SetOfCardApi {
    @RequestMapping(
        method = [RequestMethod.GET], value = ["/setOfCard/{setOfCardId}"], produces = ["application/json"]
    )
    fun findSetOfCardById(@PathVariable("setOfCardId") setOfCardId: Int): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/setOfCard/getByUserId"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun findSetOfCardByUserId(@RequestBody setOfCardDto: SetOfCardDto): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/setOfCard/new"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createSetOfCard(@RequestBody setOfCardFieldsDto: SetOfCardFieldsDto): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/setOfCard/{setOfCardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateSetOfCard(
        @PathVariable("setOfCardId") setOfCardId: Int,
        @RequestBody setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/setOfCard/{setOfCardId}"],
        produces = ["application/json"]
    )
    fun deleteSetOfCard(
        @PathVariable("setOfCardId") setOfCardId: Int
    ): ResponseEntity<Void>


    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/setOfCard/{setOfCardId}/setOfCard"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addSetOfCardToSetOfCard(
        @PathVariable("setOfCardId") setOfCardId: Int,
        @RequestBody setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto>
}
