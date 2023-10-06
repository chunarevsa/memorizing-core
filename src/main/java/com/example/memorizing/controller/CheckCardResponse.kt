package com.example.memorizing.service

import com.example.memorizing.entity.Card

data class CheckCardResponse(
    val isCorrectAnswer: Boolean,
    val card: Card
)
