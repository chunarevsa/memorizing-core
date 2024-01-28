package com.example.memorizing.storage

import org.springframework.data.repository.CrudRepository
import java.util.*

interface StorageRepository : CrudRepository<Storage, Int> {
    fun findByUserId(userId: Long): Optional<Storage>

}
