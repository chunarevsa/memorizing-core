package com.example.memorizing.repository

import com.example.memorizing.entity.Card
import org.springframework.data.repository.Repository

interface CardRepository : Repository<Card, Int> {
    fun findBySetOfCardId(ofCardId: Int): Card

}
