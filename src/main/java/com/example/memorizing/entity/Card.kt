package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonProperty

class Card(
    @get:JsonProperty
    var value: String = "",
    @get:JsonProperty
    var translate: String = "",
    @get:JsonProperty
    var type: ECardType = ECardType.UNKNOWN,
    @get:JsonProperty
    var point: Int = 0,
    @get:JsonProperty
    var status: ECardStatus = ECardStatus.NORMAL
)

