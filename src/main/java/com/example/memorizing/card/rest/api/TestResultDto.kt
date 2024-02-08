package com.example.memorizing.card.rest.api

data class TestResultDto(
    var rightAnswer: Boolean,
    var answerToOtherCard: Boolean,
    var card: CardDto
)
