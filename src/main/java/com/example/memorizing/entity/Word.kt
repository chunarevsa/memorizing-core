package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonProperty

class Word(
    @get:JsonProperty
    var value: String = "",
    @get:JsonProperty
    var translate: String = "",
    @get:JsonProperty
    var counterOfSuccessful: Int = 0,
    @get:JsonProperty
    var status: EWordStatus = EWordStatus.NORMAL
)

