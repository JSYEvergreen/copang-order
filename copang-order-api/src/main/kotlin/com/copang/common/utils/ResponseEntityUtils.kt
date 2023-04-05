package com.copang.common.utils

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.utils.AccessTokenLogger.Companion.accessTokenLogger
import com.copang.common.ApiResponse
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.client.ResourceAccessException

object ResponseEntityUtils {
    private val log = KotlinLogging.accessTokenLogger()

    fun <T> getOrThrows(
        className: String,
        methodName: String,
        errorMessage: String,
        errorType: ErrorType,
        apiCall: () -> ResponseEntity<ApiResponse<T>>,
    ): T? {
        runCatching {
            val responseEntity = apiCall()
            if (!responseEntity.statusCode.is2xxSuccessful) {
                if (responseEntity.body == null) {
                    log.error { "[$className|$methodName] 응답 body가 비어있습니다." }
                    throw CopangException(errorType)
                }
                // by-passing
                val body = requireNotNull(responseEntity.body)
                val errorCode = body.errorCode ?: ""
                val message = body.message ?: ""
                log.error {
                    "[$className|$methodName] $errorMessage, (errorCode=$errorCode, message=$message)"
                }
                throw CopangException(
                    errorCode = errorCode,
                    message = message,
                )
            }

            if (responseEntity.body == null) {
                log.error { "[$className|$methodName] 응답 body가 비어있습니다." }
                throw CopangException(errorType)
            }
            if (responseEntity.body?.isSuccess != true) {
                log.warn { "[$className|$methodName] 응답 http status는 200으로 전달받았지만 응답 body의 isSuccess가 false 입니다." }
                throw CopangException(errorType)
            }
            return responseEntity.body!!.content
        }.getOrElse {
            if (it is ResourceAccessException) {
                log.error { "[$className|$methodName] 서버 호출에서 timeout이 발생했습니다." }
                throw CopangException(errorType)
            }
            throw it
        }
    }
}
