package com.example.memorizing.setOfCard

import com.example.memorizing.card.Card
import com.example.memorizing.entity.ELanguage
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.*

data class SetOfCard(
    @Id
    var id: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var pair: Pair<ELanguage, ELanguage>? = null,
    var maxPoint: Int = 5,

    @Column("root_of_set_id")
    var rootOfSetId: Int? = null,

    @MappedCollection(idColumn = "id", keyColumn = "id")
    val listOfCards: MutableList<Card> = mutableListOf()
)

