package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class LanguageCards(
    val languageType: Pair<ELanguage, ELanguage>,

    @get:JsonProperty
    val mapOfCards: MutableMap<String, Card> = mutableMapOf()
)
