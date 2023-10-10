package com.example.memorizing.entity

data class Card(
    var value: String = "",
    var translate: String = "",
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