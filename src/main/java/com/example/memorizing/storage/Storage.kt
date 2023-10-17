package com.example.memorizing.storage

import com.example.memorizing.cardStock.CardStock
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import javax.validation.constraints.NotEmpty

data class Storage(
    @NotEmpty
    val userId: Int? = null,
) {
    @Id
    var id: Int? = null

    @MappedCollection(idColumn = "id", keyColumn = "id")
    var cardStocks: MutableList<CardStock> = mutableListOf()
}