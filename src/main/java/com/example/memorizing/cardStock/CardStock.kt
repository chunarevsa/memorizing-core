package com.example.memorizing.cardStock

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
}


