package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
object Cards {

    val listOfCardsByELanguageType = mutableListOf<LanguageCards>()

    @get:JsonProperty
    val fileName = "cards.json"
}