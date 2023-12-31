package com.example.memorizing.storage
//
//import com.example.memorizing.config.ApplicationTestConfig
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.BDDMockito.given
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.web.WebAppConfiguration
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
//import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * Test class for {@link StorageController}
 *
 * @author Chunarev Sergei
 */
//
//@SpringBootTest
//@ContextConfiguration(classes = [ApplicationTestConfig::class])
//@WebAppConfiguration
//class StorageControllerTests {
//
//    @Autowired
//    private lateinit var storageController: StorageController
//
//    @Autowired
//    private lateinit var storageMapper: StorageMapper
//
//    @MockBean
//    private lateinit var storageService: StorageService
//
//    private lateinit var mockMvc: MockMvc
//    private val mapper: ObjectMapper = ObjectMapper()
//    private val testStorages: MutableList<Storage> = mutableListOf()
//
//    companion object {
//        private const val TEST_STORAGE_ID = 1
//    }
//
//    @BeforeEach
//    fun initStorages() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(storageController)
//            .build()
//
//        val storage = Storage(1, "Test name 1").apply {
//            this.id = TEST_STORAGE_ID
//        }
//
//        testStorages.add(storage)
//    }
//
//    @Test
//    fun testGetStorageSuccess() {
//        given(storageService.findStorageById(TEST_STORAGE_ID)).willReturn(testStorages[0])
//
//        val storage = storageService.findStorageById(TEST_STORAGE_ID)
//
//        this.mockMvc.perform(get("/storage/$TEST_STORAGE_ID")
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType("application/json"))
//            .andExpect(jsonPath("$.id").value(TEST_STORAGE_ID))
//            .andExpect(jsonPath("$.storageName").value(storage?.storageName))
//    }
//
//    @Test
//    fun testGetStorageNotFound() {
//        given(storageService.findStorageById(TEST_STORAGE_ID + 1)).willReturn(null)
//
//        this.mockMvc.perform(get("/storage/$TEST_STORAGE_ID")
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isNotFound)
//    }
//
////
////    @Test
////    fun testCreateStorageSuccess() {
////        val storageDto = storageMapper.toStorageDto(storageService.findStorageById(TEST_STORAGE_ID))
////        storageDto?.id = null
////        val storageAsJSON: String = mapper.writeValueAsString(storageDto)
////
////        this.mockMvc.perform(
////            post("/storage")
////                .content(storageAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////        )
////            .andExpect(status().isCreated)
////    }
////
////    @Test
////    fun testCreateStorageNotFound() {
////        val storageDto = storageMapper.toStorageDto(storageService.findStorageById(TEST_STORAGE_ID))
////        storageDto?.id = null
////        val storageAsJSON: String = mapper.writeValueAsString(storageDto)
////
////        this.mockMvc.perform(
////            post("/storage")
////                .content(storageAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////        )
////            .andExpect(status().isCreated)
////    }
//
////
////    @Test
////    fun testCreateStorageError() {
////        val storageFieldsDto = StorageFieldsDto(null, null)
////        val req: String = mapper.writeValueAsString(storageFieldsDto)
////
////        this.mockMvc.perform(
////            post("/storage")
////                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////        )
////            .andExpect(status().isBadRequest)
////    }
////
////    @Test
////    fun testCreateStorageWithExistingUserIdError() {
////        val storageDto = storages[0]
////
////        val storageFieldsDto = StorageFieldsDto(storageDto.userId, null)
////        val req: String = mapper.writeValueAsString(storageFieldsDto)
////
////        this.mockMvc.perform(
////            post("/storage")
////                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////        )
////            .andExpect(status().isBadRequest)
////    }
//
//}