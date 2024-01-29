package com.example.memorizing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
data class BadRequestException(
    val title: String,
    val fieldName: String,
    val fieldValue: Any?,
) : RuntimeException("$title can't have param $fieldName with value:$fieldValue")
