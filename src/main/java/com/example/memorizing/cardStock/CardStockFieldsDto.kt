package com.example.memorizing.cardStock

import com.example.memorizing.entity.ELanguage
import com.fasterxml.jackson.annotation.JsonFormat

data class CardStockFieldsDto(
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int = 5,
)
