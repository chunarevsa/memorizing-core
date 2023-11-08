package com.example.memorizing.storage

interface StorageService {

    //    @Transactional(readOnly = true)
    fun findStorageById(storageId: Int): Storage?

    //    @Transactional(readOnly = true)
    fun findStorageByUserId(userId: Long): Storage?

    //    @Transactional
    fun createStorage(userId: Long, storageName: String): Storage?

    //    @Transactional
    fun saveStorage(storage: Storage)

    //    @Transactional
    fun deleteStorage(storage: Storage)
}
