package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
object Cards {
    @get:JsonProperty
    val mapOfCards = mutableMapOf<String, Card>()

    @get:JsonProperty
    val fileName = "cards.json"

    override fun toString(): String {
        return "Cards(mapOfCards=$mapOfCards)"
    }
}