package com.example.memorizing.card.api

data class TestResultDto(
    var rightAnswer: Boolean,
    var answerToOtherCard: Boolean,
    var card: CardDto
)
