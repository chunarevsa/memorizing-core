package com.example.memorizing.rootOfSet

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface RootOfSetApi {

    @RequestMapping(
        method = [RequestMethod.GET], value = ["/rootOfSet/{rootOfSetId}"], produces = ["application/json"]
    )
    fun getRootOfSetById(@PathVariable("rootOfSetId") rootOfSetId: Int): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/getByUserId"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getRootOfSetByUserId(@RequestBody rootOfSetDto: RootOfSetDto): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createRootOfSet(@RequestBody rootOfSetFieldsDto: RootOfSetFieldsDto): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateRootOfSet(
        @PathVariable("rootOfSetId") rootOfSetId: Int,
        @RequestBody rootOfSetFieldsDto: RootOfSetFieldsDto
    ): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/rootOfSet/{rootOfSetId}"],
        produces = ["application/json"]
    )
    fun deleteRootOfSet(
        @PathVariable("rootOfSetId") rootOfSetId: Int
    ): ResponseEntity<Void>

}
