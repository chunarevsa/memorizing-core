package com.example.memorizing.storage

interface StorageService {
    fun findById(storageId: Int): Storage
    fun findByUserId(userId: Long): Storage
    fun create(fields: StorageFieldsDto): Storage
    fun update(storageId: Int, fields: StorageFieldsDto): Storage
    fun delete(storage: Storage)
    fun deleteById(storageId: Int)
}
