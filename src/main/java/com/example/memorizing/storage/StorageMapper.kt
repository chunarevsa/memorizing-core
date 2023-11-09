package com.example.memorizing.storage

interface StorageMapper {
    fun toStorageDto(storage: Storage?): StorageDto?
    fun toStorage(storageDto: StorageDto?): Storage?
    fun toStorage(storageFieldsDto: StorageFieldsDto?): Storage?
}