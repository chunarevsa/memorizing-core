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
    fun findByUserId(@Param("userId") userId: Long): Storage?

    fun save(storage: Storage)

    fun existsByUserId(userId: Long): Boolean
    fun delete(storage: Storage)


}
