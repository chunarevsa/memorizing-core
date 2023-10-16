package com.example.memorizing.rootOfSet

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface RootOfSetApi {

    @RequestMapping(
        method = [RequestMethod.GET], value = ["/rootOfSet/{rootOfSetId}"], produces = ["application/json"]
    )
    fun findRootOfSetById(@PathVariable("rootOfSetId") rootOfSetId: Int): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/getByUserId"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun findRootOfSetByUserId(@RequestBody req: RootOfSetDto): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/new"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createRootOfSet(@RequestBody req: RootOfSetDto): ResponseEntity<RootOfSetDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/rootOfSet/{rootOfSetId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateRootOfSet(
        @PathVariable("rootOfSetId") rootOfSetId: Int, @RequestBody req: RootOfSetDto
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
