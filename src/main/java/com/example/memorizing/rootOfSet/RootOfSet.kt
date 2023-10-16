package com.example.memorizing.rootOfSet

import com.example.memorizing.setOfCard.SetOfCard
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