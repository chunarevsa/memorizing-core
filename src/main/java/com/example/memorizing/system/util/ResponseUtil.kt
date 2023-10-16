package com.example.memorizing.system.util

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

object ResponseUtil {
    fun <RESP> wrapOrNotFound(maybeResponse: Optional<RESP>): ResponseEntity<RESP> {
        return wrapOrNotFound(maybeResponse, null as HttpHeaders?)
    }

    fun <RESP> wrapOrNotFound(maybeResponse: Optional<RESP>, header: HttpHeaders?): ResponseEntity<RESP> {
        return maybeResponse.map { response: RESP ->
            (ResponseEntity.ok().headers(header) as ResponseEntity.BodyBuilder).body(response)
        }.orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }
}