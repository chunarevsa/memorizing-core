package com.example.memorizing.storage

import com.example.memorizing.config.ApplicationTestConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
//@WebAppConfiguration
//@ActiveProfiles("test")
class StorageControllerTests {
    @Autowired
    private lateinit var controller: StorageController

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: StorageService

    private val mapper: ObjectMapper = ObjectMapper()
    private val testStorages: MutableList<Storage> = mutableListOf()

    companion object {
        private const val TEST_STORAGE_ID = 1
    }

    @Test
    fun startFirstTest() {
        assertThat(controller).isNotNull
    }


    @BeforeEach
    fun initStorages() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        testStorages.add(Storage(1, "Test name 1").apply {
            this.id = TEST_STORAGE_ID
        })
    }

    @Test
    fun testGetStorageSuccess() {
        given(service.findStorageById(TEST_STORAGE_ID)).willReturn(testStorages[0])

        this.mockMvc.perform(
            get("/storage/$TEST_STORAGE_ID")
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(TEST_STORAGE_ID))
            .andExpect(jsonPath("$.storageName").value(testStorages[0].storageName))
    }
//
//    @Test
//    fun testGetStorageNotFound() {
//        given(storageService.findStorageById(TEST_STORAGE_ID + 1)).willReturn(null)
//
//        this.mockMvc.perform(
//            get("/storage/$TEST_STORAGE_ID")
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//        )
//            .andExpect(status().isNotFound)
//    }
//
//
//    @Test
//    fun testCreateStorageSuccess() {
//        val oldStorage = testStorages.last()
//        val storageFieldsDto = StorageFieldsDto(userId = oldStorage.userId!! + 1, storageName = "some name")
//
//        given(storageService.createStorage(storageFieldsDto.userId!!, storageFieldsDto.storageName!!))
//            .willReturn(storageMapper.toStorage(storageFieldsDto).apply { this!!.id = oldStorage.id!! + 1})
//
//        val storageAsJSON: String = mapper.writeValueAsString(storageFieldsDto)
//
//        this.mockMvc.perform(
//            post("/storage")
//                .content(storageAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//        )
//            .andExpect(status().isCreated)
//    }
//
//    @Test
//    fun testCreateStorageBabRequestByExistingStorage() {
//
//
//
//        val storageDto = storageMapper.toStorageDto(storageService.findStorageById(TEST_STORAGE_ID))
//        storageDto?.id = null
//        val storageAsJSON: String = mapper.writeValueAsString(storageDto)
//
//        this.mockMvc.perform(
//            post("/storage")
//                .content(storageAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//        )
//            .andExpect(status().isCreated)
//    }
//
//
//    @Test
//    fun testCreateStorageError() {
//        val storageFieldsDto = StorageFieldsDto(null, null)
//        val req: String = mapper.writeValueAsString(storageFieldsDto)
//
//        this.mockMvc.perform(
//            post("/storage")
//                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//        )
//            .andExpect(status().isBadRequest)
//    }
//
//    @Test
//    fun testCreateStorageWithExistingUserIdError() {
//        val storageDto = storages[0]
//
//        val storageFieldsDto = StorageFieldsDto(storageDto.userId, null)
//        val req: String = mapper.writeValueAsString(storageFieldsDto)
//
//        this.mockMvc.perform(
//            post("/storage")
//                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//        )
//            .andExpect(status().isBadRequest)
//    }

}