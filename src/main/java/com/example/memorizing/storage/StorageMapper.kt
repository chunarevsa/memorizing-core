package com.example.memorizing.storage

object StorageMapper {

    fun toStorageDto(storage: Storage): StorageDto {
        return StorageDto(
            id = storage.id,
            userId = storage.userId,
            storageName = storage.storageName
        )
    }

    fun fromFields(fields: StorageFieldsDto, entity: Storage? = null): Storage {
        val storage = entity ?: Storage(userId = fields.userId)
        return storage.apply {
            fields.storageName?.let { this.storageName = it }
        }
    }


}