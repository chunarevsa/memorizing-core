package com.example.memorizing.storage

import org.springframework.stereotype.Service

@Service
open class StorageServiceImpl(
    private val storages: StorageRepository,
) : StorageService {
    override fun findStorageById(storageId: Int): Storage? = storages.findById(storageId)

    override fun findStorageByUserId(userId: Long): Storage? = storages.findByUserId(userId)

    override fun createStorage(userId: Long, storageName: String): Storage? {
        if (storages.existsByUserId(userId)) return null
        storages.save(Storage(userId, storageName))
        return storages.findByUserId(userId)
    }

    override fun saveStorage(storage: Storage) = storages.save(storage)
    override fun deleteStorage(storage: Storage) = storages.delete(storage)

}
