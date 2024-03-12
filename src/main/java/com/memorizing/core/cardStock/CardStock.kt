package com.memorizing.core.cardStock

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.util.*
import javax.validation.constraints.Positive

data class CardStock(
    @Column("storage_id")
    @Positive
    val storageId: Int? = null,
    var cardStockName: String? = null,
    var description: String? = null,
    var keyType: String? = null,
    var valueType: String? = null,
    var maxPoint: Int? = 5,
    var testModeIsAvailable: Boolean = true,
    var onlyFromKey: Boolean = false
) {
    @Id
    var id: Int? = null
}


