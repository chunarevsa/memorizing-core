package com.example.memorizing.storage

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class Storage(
    @NotEmpty
    @Positive
    val userId: Long? = null,
    @NotNull
    @NotBlank
    var storageName: String? = null
) {
    @Id
    @Positive
    var id: Int? = null
}