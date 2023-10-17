package com.example.memorizing.card

import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.ECardType

data class CardFieldsDto(
    var value: String? = null,
    var translate: String? = null,
    var type: ECardType = ECardType.UNKNOWN,
    var pointToNative: Int = 0,
    var pointFromNative: Int = 0,
    var statusToNative: ECardStatus = ECardStatus.NORMAL,
    var statusFromNative: ECardStatus = ECardStatus.NORMAL,
)
