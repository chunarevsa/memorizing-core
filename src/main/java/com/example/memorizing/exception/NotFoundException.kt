package com.example.memorizing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg) {
    constructor(title: String, fieldName: String, fieldValue: Any?)
    : this("$title isn't founded with param $fieldName that has value:$fieldValue")
}
