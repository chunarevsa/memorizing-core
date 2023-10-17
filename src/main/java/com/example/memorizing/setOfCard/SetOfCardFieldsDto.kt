package com.example.memorizing.setOfCard

import com.example.memorizing.entity.ELanguage
import com.fasterxml.jackson.annotation.JsonFormat

data class SetOfCardFieldsDto(
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int = 5,
)
