package com.devkuma.retry.service.impl

import com.devkuma.retry.service.RetryService
import mu.KotlinLogging
import org.springframework.retry.annotation.Recover
import java.sql.SQLException

private val log = KotlinLogging.logger {}

class RetryServiceImpl : RetryService {

    override fun retry(error: Boolean) : String {
        log.info { "Retry called. param error=$error" }
        if (error)
            throw SQLException("retry SQLException")
        return "Success"
    }

    @Recover
    fun recover(exception: SQLException, error: Boolean) : String {
        log.info { "Recover called: message=${exception.message}, param error="+ error}
        return "Success"
    }

    override fun templateRetry(error: Boolean) {
        log.info { "TemplateRetry called. param error=$error" }
        if (error)
            throw SQLException("retry SQLException")
    }
}