package com.example.memorizing.system.controller

data class CheckCardRequest(
    var setOfCardsId: String,
    var cardKey: String,
    var userValue: String,
    var translateToNative: Boolean,
)