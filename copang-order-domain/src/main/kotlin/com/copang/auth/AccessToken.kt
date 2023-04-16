package com.copang.auth

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType

private const val BEARER_PREFIX = "Bearer "

class AccessToken(
    tokenWithPrefix: String?,
) {
    private val originalToken: String

    init {
        originalToken =
            tokenWithPrefix?.removePrefix(BEARER_PREFIX) ?: throw CopangException(ErrorType.NOT_EXIST_TOKEN_ERROR)
    }

    fun withPrefix() = BEARER_PREFIX + originalToken
    fun original() = originalToken
}
