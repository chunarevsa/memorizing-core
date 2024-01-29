package com.example.memorizing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestController
class ExceptionRestControllerAdvice : ResponseEntityExceptionHandler() {

    private fun resolvePathFromWebRequest(request: WebRequest): String? {
        return try {
            (request as ServletWebRequest).request.getAttribute("javax.servlet.forward.request_uri").toString()
        } catch (ex: Exception) {
            null
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException, request: WebRequest): ApiResponse {
        return ApiResponse(false, ex.message, ex.javaClass.name, resolvePathFromWebRequest(request))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleResourceNotFoundException(ex: NotFoundException, request: WebRequest): ApiResponse {
        return ApiResponse(false, ex.message, ex.javaClass.name, resolvePathFromWebRequest(request))
    }


}