package com.example.memorizing.storage

import com.example.memorizing.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
open class StorageServiceImpl(
    private val storages: StorageRepository,
) : StorageService {

    companion object {
        const val ENTITY_NAME = "storage"
    }

    override fun findById(storageId: Int): Storage =
        storages.findById(storageId).orElseThrow { NotFoundException(ENTITY_NAME, "storageId", storageId) }

    override fun findByUserId(userId: Long): Storage =
        storages.findByUserId(userId).orElseThrow { NotFoundException(ENTITY_NAME, "userId", userId) }

    override fun create(userId: Long, storageName: String): Storage = storages.save(Storage(userId, storageName))

    override fun save(storage: Storage): Storage = storages.save(storage)
    override fun delete(storage: Storage) = storages.delete(storage)

    override fun deleteById(storageId: Int) = storages.deleteById(storageId)

}
