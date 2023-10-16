package com.example.memorizing.card

import com.example.memorizing.card.Card
import org.springframework.data.repository.Repository

interface CardRepository : Repository<Card, Int> {
    fun findBySetOfCardId(ofCardId: Int): Card

}
