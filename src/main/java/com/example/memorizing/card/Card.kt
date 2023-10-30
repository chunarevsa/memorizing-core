package com.example.memorizing.card

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column


data class Card(
    @Column("card_stock_id")
    var cardStockId: Int? = null,
    var cardKey: String? = null,
    var cardValue: String? = null,
    var statusToKey: ECardStatus = ECardStatus.NOT_SUPPORTED
) {
    @Id
    var id: Int? = null
    var pointFromKey: Int = 0
    var pointToKey: Int = 0
    var statusFromKey: ECardStatus = ECardStatus.NORMAL

    fun increasePoint(isFromKey: Boolean, userMaxPoint: Int) {
        if (isFromKey && (statusToKey == ECardStatus.NOT_SUPPORTED || statusToKey == null)) return

        var status: ECardStatus = if (isFromKey) statusFromKey else statusToKey!!
        var point: Int = if (isFromKey) pointFromKey else pointToKey

        if (status == ECardStatus.HARD) {
            status = ECardStatus.NORMAL
            point = 0
        }

        if (point >= userMaxPoint) {
            point += 1
            status = ECardStatus.COMPLETED
        } else point += 1

        if (isFromKey) {
            statusFromKey = status
            pointFromKey = point
        } else {
            statusToKey = status
            pointToKey = point
        }

    }

    fun decreasePoint(isFromKey: Boolean) {
        if (isFromKey && (statusToKey == ECardStatus.NOT_SUPPORTED || statusToKey == null)) return

        var status: ECardStatus = if (isFromKey) statusFromKey else statusToKey!!
        var point: Int = if (isFromKey) pointFromKey else pointToKey

        if (status == ECardStatus.HARD) point -= 1 else point = -1
        status = ECardStatus.HARD

        if (isFromKey) {
            statusFromKey = status
            pointFromKey = point
        } else {
            statusToKey = status
            pointToKey = point
        }
    }

}