package com.example.memorizing.util

import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriComponentsBuilder

object HeaderUtil {

    private fun createAlert(
        applicationName: String,
        message: String?,
        param: String?,
        location: String? = null
    ): HttpHeaders {
        val headers = HttpHeaders()
        headers.add("X-$applicationName-alert", message)
        headers.add("X-$applicationName-params", param)
        headers.location = location?.let {
            UriComponentsBuilder.newInstance().path(it).build().toUri()
        }
        return headers
    }

    fun createEntityCreationAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String, location: String?
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.created" else "A new $entityName is created with identifier $param"
        return createAlert(applicationName, message, param, location)
    }

    fun createEntityUpdateAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String, location: String?
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.updated" else "A $entityName is updated with identifier $param"
        return createAlert(applicationName, message, param, location)
    }

    fun createEntityDeleteAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String, location: String?
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.delete" else "A $entityName is deleted with identifier $param"
        return createAlert(applicationName, message, param)
    }

}