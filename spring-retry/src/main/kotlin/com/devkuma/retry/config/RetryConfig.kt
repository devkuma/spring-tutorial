package com.devkuma.retry.config

import com.devkuma.retry.support.DefaultListenerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@EnableRetry
@Configuration
class RetryConfig {

    @Bean
    fun retryTemplate(): RetryTemplate {
        val fixedBackOffPolicy = FixedBackOffPolicy()
        fixedBackOffPolicy.backOffPeriod = 2000L

        val retryPolicy = SimpleRetryPolicy()
        retryPolicy.maxAttempts = 3

        val retryTemplate = RetryTemplate()
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy)
        retryTemplate.setRetryPolicy(retryPolicy)
        retryTemplate.registerListener(DefaultListenerSupport())
        return retryTemplate
    }
}