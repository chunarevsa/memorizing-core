package com.example.memorizing.storage

import org.springframework.transaction.annotation.Transactional

interface StorageService {

//    @Transactional(readOnly = true)
    fun findStorageById(storageId: Int): Storage?

//    @Transactional(readOnly = true)
    fun findStorageByUserId(userId: Int): Storage?
//    @Transactional
    fun createStorage(userId: Int, storageName: String): Storage?
//    @Transactional
    fun saveStorage(storage: Storage)

//    @Transactional
    fun deleteStorage(storage: Storage)
}
