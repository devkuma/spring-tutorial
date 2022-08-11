package com.devkuma.retry.service

import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import java.sql.SQLException

interface RetryService {

    @Retryable(value = [SQLException::class], maxAttempts = 2, backoff =  Backoff(delay = 1000))
    fun retry(error: Boolean) : String

    fun templateRetry(error: Boolean)
}