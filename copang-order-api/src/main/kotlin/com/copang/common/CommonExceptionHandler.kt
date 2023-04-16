package com.copang.common

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.utils.AccessTokenLogger.Companion.accessTokenLogger
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    private val logger = KotlinLogging.accessTokenLogger()

    @ExceptionHandler(CopangException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleCopangException(e: CopangException): ApiResponse<Any?> {
        return ApiResponse.fail(
            message = e.message,
            errorCode = e.errorCode,
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<Any?> {
        logger.warn(e) { "[CommonExceptionHandler|handleMethodArgumentNotValidException] 요청 파라미터가 잘못되었습니다." }

        val invalidParameters = e.bindingResult.allErrors.joinToString(separator = "/") {
            it as FieldError
            val field = it.field
            val message = it.defaultMessage ?: ""
            "($field: $message)"
        }
        val errorType = ErrorType.REQUEST_PARAMETER_ERROR
        return ApiResponse.fail(
            errorType = errorType,
            message = "${errorType.message} $invalidParameters",
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ApiResponse<Any?> {
        logger.error(e) { "[CommonExceptionHandler|handleException] 예상치 못한 서버 에러가 발생했습니다." }

        return ApiResponse.fail(
            errorType = ErrorType.ORDER_SERVER_ERROR,
        )
    }
}
