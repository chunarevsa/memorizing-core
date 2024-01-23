package com.example.memorizing.storage

object StorageMapper {

    fun toStorageDto(storage: Storage?): StorageDto? {
        if (storage == null) return null

        return StorageDto(
            id = storage.id,
            userId = storage.userId,
            storageName = storage.storageName
        )
    }

    fun toStorage(storageDto: StorageDto?): Storage? {
        if (storageDto == null) return null

        return Storage(
            userId = storageDto.userId,
            storageName = storageDto.storageName
        ).apply {
            this.id = storageDto.id
        }

    }

    fun toStorage(storageFieldsDto: StorageFieldsDto?): Storage? {
        if (storageFieldsDto == null) return null

        return Storage(
            userId = storageFieldsDto.userId,
            storageName = storageFieldsDto.storageName
        )
    }
}