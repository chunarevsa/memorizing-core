package com.example.memorizing.storage

import com.example.memorizing.config.ApplicationTestConfig
import com.example.memorizing.exception.BadRequestException
import com.example.memorizing.exception.NotFoundException
import com.example.memorizing.storage.api.StorageFieldsDto
import com.example.memorizing.storage.api.StorageMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * Test class for {@link StorageController}
 *
 * @author Chunarev Sergei
 */

@ExtendWith(SpringExtension::class)
@WebMvcTest(StorageController::class)
@ContextConfiguration(classes = [ApplicationTestConfig::class])
class StorageControllerTests {
    @Autowired
    private lateinit var controller: StorageController

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: StorageService

    private val mapper: ObjectMapper = ObjectMapper()
    private val storages = listOf(
        Storage(1, "Test name 1").apply { this.id = 1 },
        Storage(2, "Test name 2").apply { this.id = 2 },
        Storage(3, "Test name 3").apply { this.id = 3 },
    )

    @BeforeEach
    fun init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun getStorageById_Success() {
        val storage: Storage = storages.last()
        given(service.findById(storage.id!!)).willReturn(storage)

        this.mockMvc.perform(
            get("/storage/${storage.id}")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(storage.id))
            .andExpect(jsonPath("$.userId").value(storage.userId))
            .andExpect(jsonPath("$.storageName").value(storage.storageName))
    }

    @Test
    fun getStorageById_NotFound() {
        val storageId: Int = -1
        given(service.findById(storageId)).willThrow(NotFoundException("storage", "storageId", storageId))

        this.mockMvc.perform(
            get("/storage/$storageId")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun getStorageByUserId_Success() {
        val storage: Storage = storages.last()
        val storageFields = StorageFieldsDto(storage.userId, storage.storageName)

        given(service.findByUserId(storageFields.userId!!)).willReturn(storage)

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)
        this.mockMvc.perform(
            post("/storage/getByUserId")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(storage.id))
            .andExpect(jsonPath("$.userId").value(storage.userId))
            .andExpect(jsonPath("$.storageName").value(storage.storageName))
    }

    @Test
    fun getStorageByUserId_NotFound() {
        val userId: Long = -1
        val storageFields = StorageFieldsDto(userId, storages.last().storageName)
        given(service.findByUserId(userId)).willThrow(NotFoundException("storage", "userId", userId))

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)
        this.mockMvc.perform(
            post("/storage/getByUserId")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun createStorage_Success() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(storage.userId!!, storage.storageName)

        given(service.create(storageFields))
            .willReturn(storage)

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/create")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(storage.id))
            .andExpect(jsonPath("$.userId").value(storage.userId))
            .andExpect(jsonPath("$.storageName").value(storage.storageName))
    }

    @Test
    fun createStorage_BadRequest_StorageWithUserIdIsExist() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(storage.userId!!, storage.storageName)

        given(service.create(storageFields))
            .willThrow(BadRequestException(""))

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/create")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isBadRequest)
    }


    @Test
    fun updateStorage_Success() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(null, storage.storageName)

        given(service.update(storage.id!!, storageFields))
            .willReturn(StorageMapper.fromFields(storageFields, storage))

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/${storage.id}/update")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isNoContent)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(storage.id))
            .andExpect(jsonPath("$.userId").value(storage.userId))
            .andExpect(jsonPath("$.storageName").value(storageFields.storageName))
    }

    @Test
    fun updateStorage_BadRequest_NotEnoughData() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(null, null)

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/${storage.id}/update")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun updateStorage_BadRequest_UserIdIsNotNull() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(storage.userId, null)

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/${storage.id}/update")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun updateStorage_NotFound() {
        val storage = storages.last()
        val storageFields = StorageFieldsDto(null, storage.storageName)

        given(service.update(storage.id!!, storageFields))
            .willThrow(NotFoundException("storage", "storageId", storage.id))

        val storageFieldsAsJSON: String = mapper.writeValueAsString(storageFields)

        this.mockMvc.perform(
            post("/storage/${storage.id!!}/update")
                .content(storageFieldsAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun deleteStorage_Success() {
        val storage = storages.last()

        doNothing().`when`(service).deleteById(storage.id!!)

        this.mockMvc.perform(
            post("/storage/${storage.id!!}/delete")
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun deleteStorage_NotFound() {
        val storage = storages.last()

        given(service.deleteById(storage.id!!))
            .willThrow(NotFoundException("storage", "storageId", storage.id))

        this.mockMvc.perform(
            post("/storage/${storage.id!!}/delete")
        )
            .andExpect(status().isNotFound)
    }

}