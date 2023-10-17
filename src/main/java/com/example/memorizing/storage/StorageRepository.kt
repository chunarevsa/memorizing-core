package com.example.memorizing.storage

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface StorageRepository : Repository<Storage, Int> {

    @Transactional(readOnly = true)
    fun findById(id: Int): Storage?

    @Transactional(readOnly = true)
    @Query("select * from storage where user_id = :userId")
    fun findByUserId(@Param("userId") userId: Int): Storage?

    fun saveStorage(storage: Storage): Storage

    fun existsByUserId(userId: Int): Boolean
    fun delete(storage: Storage)


}
