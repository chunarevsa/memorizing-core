package com.example.memorizing.repository

import com.example.memorizing.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.File

abstract class AFileRepository {

    companion object {
        const val PREF_NEW_WORDS_FILE = "newWords"
        const val PREF_NEW_PHRASES_FILE = "newPhrases"

        const val FILE_PATH = "src/main/resources/data"
    }
    val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)

    abstract val fileName: String
    abstract val entity: Class<*>

    fun <T> save(newEntity: T) {
        val file = File(FILE_PATH + fileName)
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, newEntity)
    }

    fun load(): Any? {
        val file = File(fileName)
        return if (file.exists()) {
            mapper.readValue(file, entity)
        } else {
            file.createNewFile()
            mapper.readValue(file, entity)
        }
    }


}
