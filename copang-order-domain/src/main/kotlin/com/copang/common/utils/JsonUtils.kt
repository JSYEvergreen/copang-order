package com.copang.common.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonUtils {
    val objectMapper = jacksonObjectMapper()

    inline fun <reified T> String.toJsonOrNull(): T? = runCatching {
        objectMapper.readValue(this, T::class.java)
    }.getOrDefault(null)

    fun Any.toJsonString(): String = runCatching {
        objectMapper.writeValueAsString(this)
    }.getOrDefault("")

    fun Any.toPrettyJsonString(): String = runCatching {
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    }.getOrDefault("")
}
