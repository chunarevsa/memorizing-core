package com.example.memorizing.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

abstract class AFileRepository {

    private val logger: Logger = LogManager.getLogger(AFileRepository::class.java)

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
        logger.info("try to write file: ${file.name}")
        mapper.writeValue(file, newEntity)
        logger.info("writing is successful!")
    }

    fun load(dynamicPartOfName: String = ""): Any? {
        val file = File("$FILE_PATH$pref${dynamicPartOfName}.json")
        logger.info("try to read file: ${file.path}")
        return if (!file.exists()) {
            logger.info("file ${file.name} is not found")
            null
        } else {
            val readValue = mapper.readValue(file, entity)
            logger.info("reading is successful!")
            readValue
        }


    }

    fun findFilesByPref(prefix: String) = File(FILE_PATH).listFiles()?.filter { it.name.startsWith(prefix) }

}
