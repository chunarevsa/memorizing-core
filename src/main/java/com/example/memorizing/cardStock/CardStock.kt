package com.example.memorizing.cardStock

import com.example.memorizing.card.Card
import com.example.memorizing.entity.ELanguage
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.*

data class CardStock(
    @Id
    var id: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int = 5,

    @Column("storage_id")
    var storageId: Int? = null,

    @MappedCollection(idColumn = "id", keyColumn = "id")
    val cards: MutableList<Card> = mutableListOf()
)

