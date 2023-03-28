package com.copang.web

import com.copang.exception.ErrorType

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val content: T? = null,
    val errorCode: String? = null,
    val message: String? = null,
) {
    companion object {
        fun <R> success(content: R?) = ApiResponse(
            isSuccess = true,
            content = content,
        )

        fun <R> fail(errorType: ErrorType) = ApiResponse<R>(
            isSuccess = false,
            errorCode = errorType.errorCode,
            message = errorType.message,
        )

        fun <R> fail(errorCode: String? = "", message: String? = "") = ApiResponse<R>(
            isSuccess = false,
            errorCode = errorCode,
            message = message,
        )
    }
}
