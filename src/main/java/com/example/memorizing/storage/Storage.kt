package com.example.memorizing.storage

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotEmpty

data class Storage(
    @NotEmpty
    val userId: Long? = null,
    var storageName: String? = null
) {
    @Id
    var id: Int? = null
}