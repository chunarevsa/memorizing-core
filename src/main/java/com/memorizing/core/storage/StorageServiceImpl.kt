package com.memorizing.core.storage

import com.memorizing.core.exception.NotFoundException
import com.memorizing.core.storage.StorageController.Companion.ENTITY_NAME
import com.memorizing.core.storage.api.StorageFieldsDto
import com.memorizing.core.storage.api.StorageMapper
import org.springframework.stereotype.Service

@Service
open class StorageServiceImpl(
    private val storages: StorageRepository,
) : StorageService {

    override fun findById(storageId: Int): Storage =
        storages.findById(storageId).orElseThrow { NotFoundException(ENTITY_NAME, "storageId", storageId) }
    override fun findByUserId(userId: Long): Storage =
        storages.findByUserId(userId).orElseThrow { NotFoundException(ENTITY_NAME, "userId", userId) }
    override fun create(fields: StorageFieldsDto): Storage = save(StorageMapper.fromFields(fields))
    override fun update(storageId: Int, fields: StorageFieldsDto): Storage =
        save(StorageMapper.fromFields(fields, findById(storageId)))

    override fun delete(storage: Storage) = storages.delete(storage)
    override fun deleteById(storageId: Int) = storages.deleteById(storageId)
    private fun save(storage: Storage): Storage = storages.save(storage)

}
