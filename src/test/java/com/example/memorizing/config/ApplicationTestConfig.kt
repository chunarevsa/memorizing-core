package com.example.memorizing.config

import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
open class ApplicationTestConfig {

    init {
        MockitoAnnotations.openMocks(this)
    }

}