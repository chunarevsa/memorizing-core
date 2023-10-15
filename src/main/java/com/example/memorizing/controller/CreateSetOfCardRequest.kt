package com.example.memorizing.controller

import com.example.memorizing.entity.ELanguage
import com.fasterxml.jackson.annotation.JsonFormat

data class CreateSetOfCardRequest(
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    val pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int = 5
)