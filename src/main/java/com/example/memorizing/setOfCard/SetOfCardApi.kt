package com.example.memorizing.setOfCard

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface SetOfCardApi {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}"],
        produces = ["application/json"]
    )
    fun getSetOfCardById(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int
    ): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/new"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun addSetOfCardToRootOfSet(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @RequestBody setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateSetOfCard(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int,
        @RequestBody setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/rootOfSet/{rootOfSetId}/setOfCard/{setOfCardId}"],
        produces = ["application/json"]
    )
    fun deleteSetOfCard(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @PathVariable("setOfCardId") setOfCardId: Int
    ): ResponseEntity<Void>
}
