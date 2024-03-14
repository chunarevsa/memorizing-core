package com.memorizing.core.card.rest.api

data class TestResultDto(
    var rightAnswer: Boolean,
    var answerToOtherCard: Boolean,
    var card: CardDto
)
