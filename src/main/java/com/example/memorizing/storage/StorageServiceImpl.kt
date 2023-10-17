package com.example.memorizing.storage

import org.springframework.stereotype.Service

@Service
open class StorageServiceImpl(
    private val storages: StorageRepository,
) : StorageService {
    override fun findStorageById(storageId: Int): Storage? = storages.findById(storageId)

    override fun findStorageByUserId(userId: Int): Storage? = storages.findByUserId(userId)

    override fun create(userId: Int): Storage? {
        if (storages.existsByUserId(userId)) return null
        return storages.saveStorage(Storage(userId))
    }

    override fun saveStorage(storage: Storage) = storages.saveStorage(storage)
    override fun deleteStorage(storage: Storage) = storages.delete(storage)

}
