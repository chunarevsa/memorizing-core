package com.example.memorizing.entity

data class Card(
    var value: String = "",
    var translate: String = "",
    var type: ECardType = ECardType.UNKNOWN,
    var pointToNative: Int = 0,
    var pointFromNative: Int = 0,
    var status: ECardStatus = ECardStatus.NORMAL
)