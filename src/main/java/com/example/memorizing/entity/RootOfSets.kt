package com.example.memorizing.entity

import java.util.*

data class RootOfSets(
    val username: String? = null
) {
    val id: String = UUID.randomUUID().toString()
    val setOfCardsIds = mutableSetOf<String>()
}