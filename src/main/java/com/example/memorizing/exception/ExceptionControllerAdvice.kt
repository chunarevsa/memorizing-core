package com.example.memorizing.exception

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest

/**
 * @author Chunarev Sergei
 */

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ResponseEntity<String> {
        val mapper = ObjectMapper()
        val errorInfo: ErrorInfo = ErrorInfo(e)
        var respJSONString = "{}"
        try {
            respJSONString = mapper.writeValueAsString(errorInfo)
        } catch (e1: JsonProcessingException) {
            e1.printStackTrace()
        }
        return ResponseEntity.badRequest().body(respJSONString)
    }

    /**
     * Handles exception thrown by Bean Validation on controller methods parameters
     *
     * @param ex      The thrown exception
     * @param request the current web request
     * @return an empty response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<Void> {
        val errors = BindingErrorsResponse()
        val bindingResult = ex.bindingResult
        val headers = HttpHeaders()
        if (bindingResult.hasErrors()) {
            errors.addAllErrors(bindingResult)
            headers.add("errors", errors.toJSON())
        }
        return ResponseEntity(headers, HttpStatus.BAD_REQUEST)
    }

    private inner class ErrorInfo(ex: Exception) {
        val className: String
        val exMessage: String

        init {
            className = ex.javaClass.name
            exMessage = ex.localizedMessage
        }
    }
}
