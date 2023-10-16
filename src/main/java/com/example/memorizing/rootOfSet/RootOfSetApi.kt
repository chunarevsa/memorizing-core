package com.example.memorizing.rootOfSet

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface RootOfSetApi {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/rootOfSet/{rootOfSetId}"],
        produces = ["application/json"]
    )
    fun getRootOfSetById(@PathVariable("rootOfSetId") rootOfSetId: Int): ResponseEntity<RootOfSet>
}
