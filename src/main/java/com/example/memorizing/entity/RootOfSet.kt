package com.example.memorizing.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import javax.validation.constraints.NotEmpty

data class RootOfSet(
    @Id
    var id: Int? = null,

    @NotEmpty
    val userId: Int? = null,

    @MappedCollection(idColumn = "id", keyColumn = "id")
    var listSetOfCard: MutableList<SetOfCard> = mutableListOf()
)