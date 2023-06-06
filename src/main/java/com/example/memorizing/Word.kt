package com.example.memorizing

import com.fasterxml.jackson.annotation.JsonProperty

class Word(
    @get:JsonProperty
    var value: String,
    @get:JsonProperty
    var translate: String,
    @get:JsonProperty
    var counterOfSuccessful: Int = 0,
) {
    // for serialize
    constructor() : this("","", 0)
}

