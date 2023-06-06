package com.copang.common.utils

import com.copang.common.exception.ErrorType
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val isSuccess: Boolean,
    val content: T? = null,
    val errorCode: String? = null,
    val message: String? = null,
) {
    companion object {
        fun <R> success(content: R? = null) = ApiResponse(
            isSuccess = true,
            content = content,
        )

        fun <R> fail(errorType: ErrorType, message: String? = null) = ApiResponse<R>(
            isSuccess = false,
            errorCode = errorType.errorCode,
            message = message ?: errorType.message,
        )

        fun <R> fail(errorCode: String? = "", message: String? = "") = ApiResponse<R>(
            isSuccess = false,
            errorCode = errorCode,
            message = message,
        )
    }
}
