package com.example.memorizing.repository.file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

abstract class AFileRepository {
    companion object {
        const val FILE_PATH = "src/main/resources/data/"
    }

    val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(SerializationFeature.INDENT_OUTPUT, true)

    abstract val pref: String
    abstract val entity: Class<*>

    fun <T> save(newEntity: T, dynamicPartOfName: String = "") {
        val file = File("$FILE_PATH$pref$dynamicPartOfName.json")
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, newEntity)
    }

    fun load(dynamicPartOfName: String = ""): Any? {
        val file = File("$FILE_PATH$pref${dynamicPartOfName}.json")
        return if (!file.exists()) null else mapper.readValue(file, entity)
    }

    fun findFilesByPref(prefix: String) = File(FILE_PATH).listFiles()?.filter { it.name.startsWith(prefix) }

}
