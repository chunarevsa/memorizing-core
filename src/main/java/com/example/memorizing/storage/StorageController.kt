package com.example.memorizing.storage

import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping
class StorageController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val storageService: StorageService,
) : StorageApi {
    companion object {
        const val ENTITY_NAME = "storage"
    }

    override fun getStorageById(storageId: Int): ResponseEntity<StorageDto> {
        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = StorageDto().apply {
            this.id = storage.id
            this.userId = storage.userId
            this.storageName = storage.storageName
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun getStorageByUserId(storageDto: StorageDto): ResponseEntity<StorageDto> {
        storageDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage =
            storageService.findStorageByUserId(storageDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = StorageDto().apply {
            this.id = storage.id
            this.userId = storage.userId
            this.storageName = storage.storageName
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun createStorage(storageFieldsDto: StorageFieldsDto): ResponseEntity<StorageDto> {
        storageFieldsDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage =
            storageService.create(storageFieldsDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = StorageDto().apply {
            this.id = storage.id
            this.userId = storage.userId
            this.storageName = storage.storageName
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString()
            )
        )

        headers.location = UriComponentsBuilder.newInstance()
            .path("/storage/{id}").buildAndExpand(storage.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateStorage(
        storageId: Int,
        storageFieldsDto: StorageFieldsDto
    ): ResponseEntity<StorageDto> {
        storageFieldsDto.storageName ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage = storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        storage.apply {
            this.storageName = storageFieldsDto.storageName
        }

        storageService.saveStorage(storage)

        val result = StorageDto().apply {
            this.id = storage.id
            this.userId = storage.userId
            this.storageName = storage.storageName
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString()
            )
        )

        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteStorage(storageId: Int): ResponseEntity<Void> {
        val storage =
            storageService.findStorageById(storageId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        storageService.deleteStorage(storage)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, storage.id.toString()
            )
        )

        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}