package com.example.memorizing.system.entity

data class Mode(
    val fromKey: Boolean?,
    val modeType: EModeType,
    val listOfCardStatus: ArrayList<ECardStatus>,
)
