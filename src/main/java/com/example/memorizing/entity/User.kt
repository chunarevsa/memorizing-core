package com.example.memorizing.entity

data class User(
    val username: String,
    // settings
    val nativeLanguage: ELanguage,
    var maxPoint: Int = 5
)