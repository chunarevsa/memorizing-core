package com.example.memorizing.card

object CardMapper {

    fun toCardDto(card: Card): CardDto {
        return CardDto().apply {
            this.id = card.id
            this.cardKey = card.cardKey
            this.cardValue = card.cardValue
            this.pointToKey = card.pointToKey
            this.pointFromKey = card.pointFromKey
            this.statusToKey = card.statusToKey
            this.statusFromKey = card.statusFromKey
        }
    }

    fun fromFields(fields: CardFieldsDto, entity: Card? = null): Card {
        val card = entity ?: Card(
            cardStockId = fields.cardStockId,
            cardKey = fields.cardKey,
            statusToKey = if (fields.onlyFromKey == true) ECardStatus.NOT_SUPPORTED else ECardStatus.NORMAL
        )
        return card.apply {
            fields.cardValue?.let { this.cardValue = it }
        }
    }
}
