package com.example.memorizing.card

import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.ECardType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class Card(
    @Id
    var id: Int? = null,

    @Column("set_of_card_id")
    var setOfCardId: Int? = null,

    var value: String? = null,
    var translate: String? = null,
    var type: ECardType = ECardType.UNKNOWN,
    var pointToNative: Int = 0,
    var pointFromNative: Int = 0,
    var statusToNative: ECardStatus = ECardStatus.NORMAL,
    var statusFromNative: ECardStatus = ECardStatus.NORMAL,
) {
    fun increasePoint(toNative: Boolean, userMaxPoint: Int) {
        var status: ECardStatus = if (toNative) statusToNative else statusFromNative
        var point: Int = if (toNative) pointToNative else pointFromNative

        if (status == ECardStatus.HARD) {
            status = ECardStatus.NORMAL
            point = 0
        }

        if (point >= userMaxPoint) {
            point += 1
            status = ECardStatus.COMPLETED
        } else point += 1


        if (toNative) {
            statusToNative = status
            pointToNative = point
        } else {
            statusFromNative = status
            pointFromNative = point
        }

    }

    fun decreasePoint(toNative: Boolean) {
        var status: ECardStatus = if (toNative) statusToNative else statusFromNative
        var point: Int = if (toNative) pointToNative else pointFromNative

        if (status == ECardStatus.HARD) point -= 1 else point = -1
        status = ECardStatus.HARD

        if (toNative) {
            statusToNative = status
            pointToNative = point
        } else {
            statusFromNative = status
            pointFromNative = point
        }
    }

}