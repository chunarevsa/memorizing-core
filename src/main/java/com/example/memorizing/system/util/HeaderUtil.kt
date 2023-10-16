package com.example.memorizing.system.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders

object HeaderUtil {
    private val log: Logger = LoggerFactory.getLogger(HeaderUtil::class.java)

    private fun createAlert(applicationName: String, message: String?, param: String?): HttpHeaders {
        val headers = HttpHeaders()
        headers.add("X-$applicationName-alert", message)
        headers.add("X-$applicationName-params", param)
        return headers
    }

    fun createEntityCreationAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.created" else "A new $entityName is created with identifier $param"
        return createAlert(applicationName, message, param)

    }

    fun createEntityUpdateRoleAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.updated" else "A $entityName is updated with identifier $param"
        return createAlert(applicationName, message, param)
    }

    fun createEntityUpdateAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.updated" else "A $entityName is updated with identifier $param"
        return createAlert(applicationName, message, param)
    }

    fun createEntityDeleteAlert(
        applicationName: String, enableTranslation: Boolean, entityName: String, param: String
    ): HttpHeaders {
        val message =
            if (enableTranslation) "$applicationName.$entityName.delete" else "A $entityName is delete with identifier $param"
        return createAlert(applicationName, message, param)
    }

}