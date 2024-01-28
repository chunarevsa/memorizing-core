package com.example.memorizing.storage

interface StorageService {
    fun findById(storageId: Int): Storage
    fun findByUserId(userId: Long): Storage
    fun create(userId: Long, storageName: String): Storage
    fun save(storage: Storage): Storage
    fun delete(storage: Storage)
    fun deleteById(storageId: Int)
}
