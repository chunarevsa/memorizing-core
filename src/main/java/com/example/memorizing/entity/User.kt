package com.example.memorizing.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty
    val username: String? = null,
    // settings
    @JsonProperty
    val nativeLanguage: ELanguage? = null,
    @JsonProperty
    var maxPoint: Int = 5
) {
    @JsonProperty
    val rootOfSetIds = mutableSetOf<String>()
}