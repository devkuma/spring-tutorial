package com.devkuma.retry.service

import com.devkuma.retry.config.RetryConfig
import com.devkuma.retry.service.impl.RetryServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.retry.support.RetryTemplate
import java.sql.SQLException

@SpringBootTest(
    classes = [
        RetryServiceImpl::class,
        RetryConfig::class
    ]
)
class RetryServiceTest {

    @Autowired
    private lateinit var retryService: RetryService

    @Autowired
    private lateinit var retryTemplate: RetryTemplate

    @Test
    fun `retry error=true`() {
        // Given
        val error = true

        // When
        var result = retryService.retry(error)

        // Then
        assertEquals("Success", result)
    }

    @Test
    fun `retry error=false`() {
        // Given
        val error = false

        // When
        var result = retryService.retry(error)

        // Then
        assertEquals("Success", result)
    }

    @Test
    fun templateRetry() {
        retryTemplate.execute<Any, SQLException>(
            { retryService.templateRetry(true) },
            { retryService.templateRetry(false) }
        )
    }
}