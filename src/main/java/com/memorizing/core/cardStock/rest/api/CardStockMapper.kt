package com.memorizing.core.cardStock.rest.api

import com.memorizing.core.cardStock.CardStock

object CardStockMapper {

    fun toCardStockDto(cardStock: CardStock): CardStockDto {
        return CardStockDto(
            id = cardStock.id,
            storageId = cardStock.storageId,
            cardStockName = cardStock.cardStockName,
            description = cardStock.description,
            keyType = cardStock.keyType,
            valueType = cardStock.valueType,
            maxPoint = cardStock.maxPoint,
            testModeIsAvailable = cardStock.testModeIsAvailable,
            onlyFromKey = cardStock.onlyFromKey
        )
    }

    fun fromFields(fields: CardStockFieldsDto, entity: CardStock? = null): CardStock {
        val cardStock = entity ?: CardStock(fields.storageId)
        return cardStock.apply {
            fields.cardStockName?.let { this.cardStockName = it }
            fields.description?.let { this.description = it }
            fields.keyType?.let { this.keyType = it }
            fields.valueType?.let { this.valueType = it }
            fields.maxPoint?.let { this.maxPoint = it }
            fields.testModeIsAvailable?.let { this.testModeIsAvailable = it }
            fields.onlyFromKey?.let { this.onlyFromKey = it }
        }
    }

}
