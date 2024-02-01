package com.example.memorizing.storage

import com.example.memorizing.exception.BadRequestException
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

    override fun getStorageById(storageId: Int): ResponseEntity<StorageDto> {
        log.debug("getStorageById with req: storageId = $storageId")
        val storage = storageService.findById(storageId)

        return ResponseEntity(StorageMapper.toStorageDto(storage), HttpStatus.OK)
    }

    override fun getStorageByUserId(storageFieldsDto: StorageFieldsDto): ResponseEntity<StorageDto> {
        log.debug("getStorageByUserId with req: $storageFieldsDto")
        storageFieldsDto.userId ?: throw BadRequestException(ENTITY_NAME, "userId", "null")

        val storage = storageService.findByUserId(storageFieldsDto.userId!!)

        return ResponseEntity(StorageMapper.toStorageDto(storage), HttpStatus.OK)
    }

    override fun createStorage(storageFieldsDto: StorageFieldsDto): ResponseEntity<StorageDto> {
        log.debug("createStorage with req: $storageFieldsDto")
        storageFieldsDto.userId ?: throw BadRequestException(ENTITY_NAME, "userId", "null")
        storageFieldsDto.storageName ?: throw BadRequestException(ENTITY_NAME, "storageName", "null")

        val storage = storageService.create(storageFieldsDto.userId!!, storageFieldsDto.storageName!!)

        return ResponseEntity(
            StorageMapper.toStorageDto(storage),
            HeaderUtil.createEntityCreationAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString(), "/${ENTITY_NAME}/${storage.id}"
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateStorage(
        storageId: Int,
        storageFieldsDto: StorageFieldsDto
    ): ResponseEntity<StorageDto> {
        log.debug("updateStorage with path variable $storageId and req: $storageFieldsDto")
        if (storageFieldsDto.userId != null) throw BadRequestException("userId should be null")
        storageFieldsDto.storageName ?: throw BadRequestException(ENTITY_NAME, "storageName", "null")

        val storage = storageService.findById(storageId).apply {
            storageFieldsDto.storageName.let {
                this.storageName = it
            }
        }
        storageService.save(storage)

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
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, storageId.toString(), "/${ENTITY_NAME}/$storageId"
            ),
            HttpStatus.NO_CONTENT
        )
    }

}