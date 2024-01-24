package com.example.memorizing.storage

import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id

data class Storage(
    val userId: Long? = null,
    var storageName: String? = null
) {
    @Id
    var id: Int? = null
}