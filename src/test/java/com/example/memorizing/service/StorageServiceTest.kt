package com.example.memorizing.service

import com.example.memorizing.config.ApplicationTestConfig
import com.memorizing.core.storage.StorageRepository
import com.memorizing.core.storage.StorageService
import com.memorizing.core.storage.StorageServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApplicationTestConfig::class])
class StorageServiceTest {

    @MockBean
    private lateinit var storages: StorageRepository

    private lateinit var service: StorageService

    @BeforeEach
    fun setup() {
        service = StorageServiceImpl(storages)
    }

    @Test
    fun startFirstTest() {
        assertThat(service).isNotNull
    }
}