package com.memorizing.core.cardStock

import org.springframework.data.repository.CrudRepository

interface CardStockRepository : CrudRepository<CardStock, Int> {
    fun findAllByStorageId(storageId: Int): List<CardStock>
}
