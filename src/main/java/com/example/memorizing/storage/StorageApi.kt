package com.example.memorizing.storage

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface StorageApi {

    @RequestMapping(
        method = [RequestMethod.GET], value = ["/storage/{storageId}"], produces = ["application/json"]
    )
    fun getStorageById(@PathVariable("storageId") storageId: Int): ResponseEntity<StorageDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/getByUserId"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getStorageByUserId(@RequestBody storageDto: StorageDto): ResponseEntity<StorageDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createStorage(@RequestBody storageFieldsDto: StorageFieldsDto): ResponseEntity<StorageDto>

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/storage/{storageId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateStorage(
        @PathVariable("storageId") storageId: Int,
        @RequestBody storageFieldsDto: StorageFieldsDto
    ): ResponseEntity<StorageDto>

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/storage/{storageId}"],
        produces = ["application/json"]
    )
    fun deleteStorage(
        @PathVariable("storageId") storageId: Int
    ): ResponseEntity<Void>

}
