package com.example.memorizing.card

data class TestResultDto(
    var rightAnswer: Boolean,
    var answerToOtherCard: Boolean,
    var card: CardDto
)
