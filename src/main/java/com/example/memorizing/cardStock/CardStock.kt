package com.example.memorizing.cardStock

import com.example.memorizing.card.Card
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.*

data class CardStock(
    var storageId: Int? = null,
    var name: String? = null,
    var discription: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean? = null,
    var onlyForward: Boolean? = null
) {
    @Id
    var id: Int? = null
    @Column("storage_id")

    @MappedCollection(idColumn = "id", keyColumn = "id")
    val cards: MutableList<Card> = mutableListOf()
}


