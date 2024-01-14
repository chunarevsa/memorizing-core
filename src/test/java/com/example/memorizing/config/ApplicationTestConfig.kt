package com.example.memorizing.config

import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ActiveProfiles

@TestConfiguration
open class ApplicationTestConfig {

    init {
        MockitoAnnotations.openMocks(this)
    }

}