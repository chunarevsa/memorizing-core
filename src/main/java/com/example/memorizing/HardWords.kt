package com.example.memorizing

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
object HardWords {
    @get:JsonProperty
    val mapOfWords = mutableMapOf<String, Word>()

    @get:JsonProperty
    val fileName = "hardWords.json"

    override fun toString(): String {
        return "Words(mapOfWords=$mapOfWords)"
    }
}