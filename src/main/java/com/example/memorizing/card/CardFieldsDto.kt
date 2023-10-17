package com.example.memorizing.card

import com.example.memorizing.system.entity.ECardStatus

data class CardFieldsDto(
    var key: String? = null,
    var value: String? = null,
    var statusFromKey: ECardStatus? = null,
    var statusToKey: ECardStatus? = null,
)
