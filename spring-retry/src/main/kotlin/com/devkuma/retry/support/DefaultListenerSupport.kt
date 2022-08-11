package com.devkuma.retry.support

import mu.KotlinLogging
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.listener.RetryListenerSupport

private val log = KotlinLogging.logger {}

class DefaultListenerSupport : RetryListenerSupport() {
    override fun <T, E : Throwable?> close(context: RetryContext, callback: RetryCallback<T, E>, throwable: Throwable?) {
        log.info("onClose")
        super.close(context, callback, throwable)
    }

    override fun <T, E : Throwable?> onError(
        context: RetryContext,
        callback: RetryCallback<T, E>,
        throwable: Throwable
    ) {
        log.info("onError")
        super.onError(context, callback, throwable)
    }

    override fun <T, E : Throwable?> open(context: RetryContext, callback: RetryCallback<T, E>): Boolean {
        log.info("onOpen")
        return super.open(context, callback)
    }
}