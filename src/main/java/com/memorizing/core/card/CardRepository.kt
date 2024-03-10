package com.memorizing.core.card

import org.springframework.data.repository.CrudRepository

interface CardRepository : CrudRepository<Card, Int> {
    fun findAllByCardStockId(cardStockId: Int): List<Card>
}
