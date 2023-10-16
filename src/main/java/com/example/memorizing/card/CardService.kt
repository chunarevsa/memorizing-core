package com.example.memorizing.card

import org.springframework.transaction.annotation.Transactional

interface CardService {

    @Transactional(readOnly = true)
    fun findListBySetOfCardId(setOfCardId: Int): MutableList<Card>
}
