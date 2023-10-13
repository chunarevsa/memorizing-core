package com.example.memorizing.entity

data class Mode(
    val translateToNative: Boolean?,
    val modeType: EModeType,
    val listOfCardStatus: ArrayList<ECardStatus>,
    val cardType: ECardType
)
