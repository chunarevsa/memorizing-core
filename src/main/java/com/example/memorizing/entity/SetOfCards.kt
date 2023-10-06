package com.example.memorizing.entity

import java.util.UUID

data class SetOfCards(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val pair: Pair<ELanguage, ELanguage>,
    val mapOfCards: MutableMap<String, Card> = mutableMapOf()
)

