package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonProperty

class Word(
    @get:JsonProperty
    var value: String = "",
    @get:JsonProperty
    var translate: String = "",
    @get:JsonProperty
    var type: EObjectType = EObjectType.UNKNOWN,
    @get:JsonProperty
    var point: Int = 0,
    @get:JsonProperty
    var status: EWordStatus = EWordStatus.NORMAL
)

