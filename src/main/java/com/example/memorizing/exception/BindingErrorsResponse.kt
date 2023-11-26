package com.example.memorizing.exception

//import com.fasterxml.jackson.annotation.JsonAutoDetect
//import com.fasterxml.jackson.annotation.PropertyAccessor
//import com.fasterxml.jackson.core.JsonProcessingException
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.validation.BindingResult
//
///**
// * @author Chunarev Sergei
// */
//class BindingErrorsResponse(pathId: Int?, bodyId: Int?) {
//    @JvmOverloads
//    constructor(id: Int? = null) : this(null, id) {
//    }
//
//    private fun addBodyIdError(bodyId: Int?, message: String) {
//        val error = BindingError()
//        error.setObjectName("body")
//        error.setFieldName("id")
//        error.setFieldValue(bodyId.toString())
//        error.setErrorMessage(message)
//        addError(error)
//    }
//
//    private val bindingErrors: MutableList<BindingError> = ArrayList()
//
//    init {
//        val onlyBodyIdSpecified = pathId == null && bodyId != null
//        if (onlyBodyIdSpecified) {
//            addBodyIdError(bodyId, "must not be specified")
//        }
//        val bothIdsSpecified = pathId != null && bodyId != null
//        if (bothIdsSpecified && pathId != bodyId) {
//            addBodyIdError(bodyId, String.format("does not match pathId: %d", pathId))
//        }
//    }
//
//    private fun addError(bindingError: BindingError) {
//        bindingErrors.add(bindingError)
//    }
//
//    fun addAllErrors(bindingResult: BindingResult) {
//        for (fieldError in bindingResult.fieldErrors) {
//            val error = BindingError()
//            error.setObjectName(fieldError.objectName)
//            error.setFieldName(fieldError.field)
//            error.setFieldValue(fieldError.rejectedValue.toString())
//            error.setErrorMessage(fieldError.defaultMessage)
//            addError(error)
//        }
//    }
//
//    fun toJSON(): String {
//        val mapper = ObjectMapper()
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
//        var errorsAsJSON = ""
//        try {
//            errorsAsJSON = mapper.writeValueAsString(bindingErrors)
//        } catch (e: JsonProcessingException) {
//            e.printStackTrace()
//        }
//        return errorsAsJSON
//    }
//
//    override fun toString(): String {
//        return "BindingErrorsResponse [bindingErrors=$bindingErrors]"
//    }
//
//    class BindingError {
//        private var objectName = ""
//        private var fieldName = ""
//        private var fieldValue = ""
//        private var errorMessage = ""
//        fun setObjectName(objectName: String) {
//            this.objectName = objectName
//        }
//
//        fun setFieldName(fieldName: String) {
//            this.fieldName = fieldName
//        }
//
//        fun setFieldValue(fieldValue: String) {
//            this.fieldValue = fieldValue
//        }
//
//        fun setErrorMessage(error_message: String) {
//            errorMessage = error_message
//        }
//
//        override fun toString(): String {
//            return ("BindingError [objectName=" + objectName + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue
//                    + ", errorMessage=" + errorMessage + "]")
//        }
//    }
//}
