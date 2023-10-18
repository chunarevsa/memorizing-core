package com.example.memorizing.card

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class Card(
    @Column("card_stock_id")
    var cardStockId: Int? = null,
    var cardKey: String? = null,
    var cardValue: String? = null,
) {
    @Id
    var id: Int? = null
    var pointFromKey: Int = 0
    var pointToKey: Int = 0
    var statusFromKey: ECardStatus = ECardStatus.NORMAL
    var statusToKey: ECardStatus = ECardStatus.NORMAL

    fun increasePoint(toNative: Boolean, userMaxPoint: Int) {
        var status: ECardStatus = if (toNative) statusFromKey else statusToKey
        var point: Int = if (toNative) pointFromKey else pointToKey

        if (status == ECardStatus.HARD) {
            status = ECardStatus.NORMAL
            point = 0
        }

        if (point >= userMaxPoint) {
            point += 1
            status = ECardStatus.COMPLETED
        } else point += 1


        if (toNative) {
            statusFromKey = status
            pointFromKey = point
        } else {
            statusToKey = status
            pointToKey = point
        }

    }

    fun decreasePoint(toNative: Boolean) {
        var status: ECardStatus = if (toNative) statusFromKey else statusToKey
        var point: Int = if (toNative) pointFromKey else pointToKey

        if (status == ECardStatus.HARD) point -= 1 else point = -1
        status = ECardStatus.HARD

        if (toNative) {
            statusFromKey = status
            pointFromKey = point
        } else {
            statusToKey = status
            pointToKey = point
        }
    }

}