package com.example.memorizing.controller

import com.example.memorizing.entity.ECardType

data class CheckCardRequest(
    var setOfCardsId: String,
    var cardType: ECardType,
    var cardKey: String,
    var userValue: String,
    var translateToNative: Boolean,
    var userMaxPoint: Int
)