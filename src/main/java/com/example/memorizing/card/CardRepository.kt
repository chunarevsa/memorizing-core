package com.example.memorizing.card

import org.springframework.data.repository.Repository

interface CardRepository : Repository<Card, Int> {
    fun findBySetOfCardId(ofCardId: Int): Card

    fun findAllBySetOfCardId(setOfCardId: Int): MutableList<Card>

}
