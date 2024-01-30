package com.example.memorizing.exception

import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionRestControllerAdvice : ResponseEntityExceptionHandler() {

    private fun resolvePathFromWebRequest(request: WebRequest): String? {
        return try {
            (request as ServletWebRequest).request.requestURI
        } catch (ex: Exception) {
            null
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [DbActionExecutionException::class])
    fun handleDbActionExecutionException(ex: DbActionExecutionException, request: WebRequest): ApiResponse {
        return ApiResponse(false, ex.cause?.cause?.message, ex.javaClass.name, resolvePathFromWebRequest(request))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [BadRequestException::class])
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ApiResponse {
        return ApiResponse(false, ex.message, ex.javaClass.name, resolvePathFromWebRequest(request))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleResourceNotFoundException(ex: NotFoundException, request: WebRequest): ApiResponse {
        return ApiResponse(false, ex.message, ex.javaClass.name, resolvePathFromWebRequest(request))
    }

}