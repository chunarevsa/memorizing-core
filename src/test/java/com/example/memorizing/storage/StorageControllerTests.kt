package com.example.memorizing.storage

import com.example.memorizing.exception.ExceptionControllerAdvice
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * Test class for {@link StorageController}
 *
 * @author Chunarev Sergei
 */

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
class StorageControllerTests @Autowired constructor(
    private var storageController: StorageController,
    @MockBean
    private var storageService: StorageService,
) {

    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(storageController)
        .setControllerAdvice(ExceptionControllerAdvice())
        .build()

    private val mapper: ObjectMapper = ObjectMapper()
    private val storages = mutableListOf<StorageDto>()

    @BeforeEach
    fun initStorages() {
        storages.add(StorageDto(1, 1, "name 1"))
        storages.add(StorageDto(2, 2, "name 2"))
        storages.add(StorageDto(3, 3, "name 3"))

    }


    @Test
    fun testCreateStorageSuccess() {
        val storageDto = storages[0]

        val storageFieldsDto = StorageFieldsDto(storageDto.userId, storageDto.storageName)
        val req: String = mapper.writeValueAsString(storageFieldsDto)

        this.mockMvc.perform(
            post("/storage")
                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun testCreateStorageError() {
        val storageFieldsDto = StorageFieldsDto(null, null)
        val req: String = mapper.writeValueAsString(storageFieldsDto)

        this.mockMvc.perform(
            post("/storage")
                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testCreateStorageWithExistingUserIdError() {
        val storageDto = storages[0]

        val storageFieldsDto = StorageFieldsDto(storageDto.userId, null)
        val req: String = mapper.writeValueAsString(storageFieldsDto)

        this.mockMvc.perform(
            post("/storage")
                .content(req).accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isBadRequest)
    }

}