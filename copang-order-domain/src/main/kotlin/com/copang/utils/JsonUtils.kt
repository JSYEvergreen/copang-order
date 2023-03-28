package com.copang.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonUtils {
    val objectMapper = jacksonObjectMapper()

    inline fun <reified T> String.toJsonOrNull(): T? = runCatching {
        objectMapper.readValue(this, T::class.java)
    }.getOrDefault(null)

    fun Any.toJsonString(): String = runCatching {
        objectMapper.writeValueAsString(this)
    }.getOrDefault("")
}
