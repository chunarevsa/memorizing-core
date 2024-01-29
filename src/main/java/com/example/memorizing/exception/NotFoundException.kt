package com.example.memorizing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class NotFoundException(
    val title: String,
    val fieldName: String,
    val fieldValue: Any,
) : RuntimeException("$title isn't founded with param $fieldName that has value:$fieldValue")
