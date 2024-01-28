package com.example.memorizing.storage

import com.example.memorizing.util.HeaderUtil
import org.apache.log4j.Logger
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
    private val log = Logger.getLogger(StorageController::class.java)

    companion object {
        const val ENTITY_NAME = "storage"
    }

    override fun getStorageById(storageId: Int): ResponseEntity<StorageDto> {
        log.debug("getStorageById with req: storageId = $storageId")
        val storage = storageService.findById(storageId)

        return ResponseEntity(StorageMapper.toStorageDto(storage), HttpStatus.OK)
    }

    override fun getStorageByUserId(storageDto: StorageDto): ResponseEntity<StorageDto> {
        log.debug("getStorageByUserId with req: $storageDto")
        storageDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage = storageService.findByUserId(storageDto.userId!!)

        return ResponseEntity(StorageMapper.toStorageDto(storage), HttpStatus.OK)
    }

    override fun createStorage(storageFieldsDto: StorageFieldsDto): ResponseEntity<StorageDto> {
        log.debug("createStorage with req: $storageFieldsDto")
        if (storageFieldsDto.userId == null || storageFieldsDto.storageName == null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage = storageService.create(storageFieldsDto.userId!!, storageFieldsDto.storageName!!)

        return ResponseEntity(StorageMapper.toStorageDto(storage), createHeadersWithLocation(storage.id!!), HttpStatus.CREATED)
    }

    override fun updateStorage(
        storageId: Int,
        storageFieldsDto: StorageFieldsDto
    ): ResponseEntity<StorageDto> {
        log.debug("updateStorage with path variable $storageId and req: $storageFieldsDto")
        storageFieldsDto.storageName ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val storage = storageService.findById(storageId).apply {
            storageFieldsDto.storageName.let {
                this.storageName = it }
        }
        storageService.save(storage)

        return ResponseEntity(StorageMapper.toStorageDto(storage), createHeaders(storageId), HttpStatus.NO_CONTENT)
    }

    override fun deleteStorage(storageId: Int): ResponseEntity<Void> {
        log.debug("deleteStorage with path variable $storageId ")
        storageService.deleteById(storageId)

        return ResponseEntity(createHeaders(storageId), HttpStatus.NO_CONTENT)
    }

    private fun createHeaders(id: Int): HttpHeaders {
        return HeaderUtil.createEntityDeleteAlert(
            applicationName, false, ENTITY_NAME, id.toString()
        )
    }

    private fun createHeadersWithLocation(id: Int): HttpHeaders =
        createHeaders(id).apply {
            this.location = UriComponentsBuilder.newInstance()
                .path("/${ENTITY_NAME}/{id}").buildAndExpand(id).toUri()
        }

}