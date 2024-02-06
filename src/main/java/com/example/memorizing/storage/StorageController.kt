package com.example.memorizing.storage

import com.example.memorizing.exception.BadRequestException
import com.example.memorizing.storage.api.StorageApi
import com.example.memorizing.storage.api.StorageDto
import com.example.memorizing.storage.api.StorageFieldsDto
import com.example.memorizing.storage.api.StorageMapper
import com.example.memorizing.util.HeaderUtil
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class StorageController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val storageService: StorageService,
) : StorageApi {
    private val log = Logger.getLogger(StorageController::class.java)

    companion object {
        const val ENTITY_NAME = "storage"
    }

    override fun getStorageById(storageId: Int): StorageDto {
        log.debug("getStorageById with req: storageId = $storageId")
        val storage = storageService.findById(storageId)
        return StorageMapper.toStorageDto(storage)
    }

    override fun getStorageByUserId(fields: StorageFieldsDto): StorageDto {
        log.debug("getStorageByUserId with req: $fields")
        fields.userId ?: throw BadRequestException(ENTITY_NAME, "userId", "null")

        val storage = storageService.findByUserId(fields.userId!!)
        return StorageMapper.toStorageDto(storage)
    }

    override fun createStorage(fields: StorageFieldsDto): ResponseEntity<StorageDto> {
        log.debug("createStorage with req: $fields")
        fields.userId ?: throw BadRequestException(ENTITY_NAME, "userId", "null")
        if (fields.storageName.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "storageName", "null")

        val storage = storageService.create(fields)

        return ResponseEntity(
            StorageMapper.toStorageDto(storage),
            HeaderUtil.createEntityCreationAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString(), "/${ENTITY_NAME}/${storage.id}"
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateStorage(storageId: Int, fields: StorageFieldsDto): ResponseEntity<StorageDto> {
        log.debug("updateStorage with path variable $storageId and req: $fields")
        if (fields.userId != null) throw BadRequestException("userId should be null")
        if (fields.storageName.isNullOrBlank()) throw BadRequestException(ENTITY_NAME, "storageName", "null")

        val storage = storageService.update(storageId, fields)

        return ResponseEntity(
            StorageMapper.toStorageDto(storage),
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString(), "/${ENTITY_NAME}/${storage.id}"
            ),
            HttpStatus.NO_CONTENT
        )
    }

    override fun deleteStorage(storageId: Int): ResponseEntity<Void> {
        log.debug("deleteStorage with path variable $storageId ")

        storageService.deleteById(storageId)

        return ResponseEntity(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, storageId.toString(), "/${ENTITY_NAME}/$storageId"
            ),
            HttpStatus.NO_CONTENT
        )
    }

}