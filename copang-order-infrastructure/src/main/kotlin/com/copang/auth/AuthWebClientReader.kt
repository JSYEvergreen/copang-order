package com.copang.auth

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.utils.AccessTokenLogger.Companion.accessTokenLogger
import com.copang.common.utils.ApiResponse
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.TimeoutException

@Component
class AuthWebClientReader(
    @Value("\${copang.servers.auth}") private val authServerUrl: String,
    private val copangWebClient: WebClient,
) : AuthReader {
    private val log = KotlinLogging.accessTokenLogger()

    override fun readOrThrows(accessToken: String): UserInfo {
        log.info { "[AuthWebClientReader|readOrThrows] accessToken으로 userInfo 조회 시작" }

        return runBlocking {
            val responseEntity = copangWebClient
                .method(HttpMethod.GET)
                .uri("$authServerUrl/login")
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<ApiResponse<UserInfo>>() {})
                .doOnError { e ->
                    if (e is TimeoutException) {
                        log.error(e) { "[AuthWebClientReader|readOrThrows] 서버 호출에서 timeout이 발생했습니다." }
                        throw CopangException(ErrorType.AUTH_SERVER_ERROR)
                    }
                }
                .awaitSingle()
                .also { log.info { "[AuthWebClientReader|readOrThrows] 서버 호출 완료." } }

            if (!responseEntity.statusCode.is2xxSuccessful) {
                if (responseEntity.body == null) {
                    log.error { "[AuthWebClientReader|readOrThrows] 응답 body가 비어있습니다." }
                    throw CopangException(ErrorType.AUTH_SERVER_ERROR)
                }
                // by-passing
                val body = responseEntity.body!!
                val errorCode = body.errorCode ?: ""
                val message = body.message ?: ""
                log.error {
                    "[AuthWebClientReader|readOrThrows] 유저 정보 조회 실패, (errorCode=$errorCode, message=$message)"
                }
                throw CopangException(
                    errorCode = errorCode,
                    message = message,
                )
            }

            if (responseEntity.body == null) {
                log.error { "[AuthWebClientReader|readOrThrows] 응답 body가 비어있습니다." }
                throw CopangException(ErrorType.AUTH_SERVER_ERROR)
            }
            if (responseEntity.body?.isSuccess != true) {
                log.warn { "[AuthWebClientReader|readOrThrows] 응답 http status는 200으로 전달받았지만 응답 body의 isSuccess가 false 입니다." }
                throw CopangException(ErrorType.AUTH_SERVER_ERROR)
            }
            responseEntity.body!!.content!!
        }
    }
}
