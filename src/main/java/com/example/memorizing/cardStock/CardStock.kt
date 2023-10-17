package com.example.memorizing.cardStock

import com.example.memorizing.card.Card
import com.example.memorizing.card.EModeType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.*

data class CardStock(
    var storageId: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean = false,
    var onlyFromKey: Boolean = false
) {
    @Id
    var id: Int? = null
    @Column("storage_id")

    @MappedCollection(idColumn = "id", keyColumn = "id")
    val cards: MutableList<Card> = mutableListOf()

    fun getAvailableMods(): MutableList<EModeType> {
        val mods = EModeType.values().toMutableList()
        if (!testModeIsAvailable) {
            mods.remove(EModeType.TESTING_TO_KEY)
            mods.remove(EModeType.TESTING_FROM_KEY)
        }
        if (onlyFromKey) mods.remove(EModeType.TESTING_TO_KEY)
        return mods
    }
}


