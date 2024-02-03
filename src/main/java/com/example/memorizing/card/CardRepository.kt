package com.example.memorizing.card

import org.springframework.data.repository.CrudRepository

interface CardRepository : CrudRepository<Card, Int> {
    fun findAllByCardStockId(cardStockId: Int): List<Card>
}
