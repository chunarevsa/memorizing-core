package com.memorizing.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(msg: String) : RuntimeException(msg)  {
    constructor(title: String, fieldName: String, fieldValue: Any?)
            : this("$title can't have param $fieldName with value:$fieldValue")
}
