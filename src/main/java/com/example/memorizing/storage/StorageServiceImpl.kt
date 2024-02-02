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

    override fun create(fields: StorageFieldsDto): Storage {
        val storage: Storage = StorageMapper.fromFields(fields)
        return save(storage)
    }
    override fun update(storageId: Int, fields: StorageFieldsDto): Storage {
        val storage = StorageMapper.fromFields(fields, findById(storageId))
        return save(storage)
    }

    override fun delete(storage: Storage) = storages.delete(storage)

    override fun deleteById(storageId: Int) = storages.deleteById(storageId)

    private fun save(storage: Storage): Storage = storages.save(storage)

}
