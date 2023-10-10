package com.example.memorizing.controller

import com.example.memorizing.entity.Card

data class CheckCardResponse(
    val isCorrectAnswer: Boolean,
    val card: Card
)
