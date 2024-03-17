package com.memorizing.core.exception

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse(
    var success: Boolean? = null,
    var data: String? = null,
    var cause: String? = null,
    var path: String? = null,
) {
    val timestamp: String = Instant.now().toString()
}