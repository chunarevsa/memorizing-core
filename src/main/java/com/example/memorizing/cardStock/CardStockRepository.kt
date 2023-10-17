package com.example.memorizing.cardStock

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface CardStockRepository : Repository<CardStock, Int> {

    @Transactional(readOnly = true)
    fun findById(id: Int): CardStock?

    @Transactional(readOnly = true)
    @Query("select * from set_of_card where root_of_set_id = :storageId")
    fun findAllByStorageId (@Param("storageId") storageId: Int): MutableList<CardStock>

    @Transactional
    fun saveCardStock(cardStock: CardStock): CardStock
    @Transactional
    fun deleteCardStock(cardStock: CardStock)
}
