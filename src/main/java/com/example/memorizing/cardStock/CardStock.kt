package com.example.memorizing.cardStock

import com.example.memorizing.card.EModeType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.util.*

data class CardStock(
    @Column("storage_id")
    var storageId: Int? = null,
    var cardStockName: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = null,
    var testModeIsAvailable: Boolean = false,
    var onlyFromKey: Boolean = false
) {
    @Id
    var id: Int? = null

//    @MappedCollection(idColumn = "id", keyColumn = "id")
//    val cards: MutableList<Card> = mutableListOf()

    var availableMods: MutableList<EModeType> = run{
        val mods = EModeType.values().toMutableList()
        if (!testModeIsAvailable) {
            mods.remove(EModeType.TESTING_TO_KEY)
            mods.remove(EModeType.TESTING_FROM_KEY)
        }
        if (onlyFromKey) mods.remove(EModeType.TESTING_TO_KEY)
        return@run mods
    }
}


