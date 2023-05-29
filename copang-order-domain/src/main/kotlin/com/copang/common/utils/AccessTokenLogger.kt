package com.copang.common.utils

import mu.KotlinLogging

class AccessTokenLogger private constructor(
    logger: KotlinLogging,
) {
    companion object {
        fun KotlinLogging.accessTokenLogger() = AccessTokenLogger(this)
    }

    private val log = logger.logger {}

    private val accessToken: String
        get() = runCatching {
            AuthUtils.getAccessToken().original()
        }.getOrDefault("noAccessToken")

    fun info(msg: () -> Any?) {
        log.info { "[$accessToken] ${msg()}" }
    }

    fun info(t: Throwable) {
        log.info(t) { "[$accessToken] ${t.message}" }
    }

    fun info(t: Throwable, msg: () -> Any?) {
        log.info(t) { "[$accessToken] ${msg()}" }
    }

    fun warn(msg: () -> Any?) {
        log.warn { "[$accessToken] ${msg()}" }
    }

    fun warn(t: Throwable) {
        log.warn(t) { "[$accessToken] ${t.message}" }
    }

    fun warn(t: Throwable, msg: () -> Any?) {
        log.warn(t) { "[$accessToken] ${msg()}" }
    }

    fun error(msg: () -> Any?) {
        log.error { "[$accessToken] ${msg()}" }
    }

    fun error(t: Throwable) {
        log.error(t) { "[$accessToken] ${t.message}" }
    }

    fun error(t: Throwable, msg: () -> Any?) {
        log.error(t) { "[$accessToken] ${msg()}" }
    }
}
