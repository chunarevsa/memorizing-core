package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class SetOfCards(
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    val pair: Pair<ELanguage, ELanguage>? = null
) {
    val id: String = UUID.randomUUID().toString()
    val mapOfCards: MutableMap<String, Card> = mutableMapOf()
    var maxPoint: Int = 5
}

