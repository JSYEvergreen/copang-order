package com.copang.web.interceptor

import com.copang.auth.AccessToken
import com.copang.exception.CopangException
import com.copang.exception.ErrorType
import com.copang.utils.AuthUtils
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpResponse
import org.springframework.mock.http.client.MockClientHttpRequest
import java.io.InputStream

class OutRequestAuthInterceptorTest {
    private val sut = OutRequestAuthInterceptor()
    private val givenAccessToken = AccessToken(
        tokenWithPrefix = "token",
    )

    @AfterEach
    fun tearDown() {
        AuthUtils.clearAccessToken()
    }

    @Test
    fun `외부로 나가는 요청을 가로채서 Authorization 헤더에 accessToken을 Bearer prefix와 함께 넣어준다`() {
        // given
        accessTokenIsInContext()
        val givenRequest = MockClientHttpRequest()
        val givenBody = ByteArray(size = 1)
        val givenExecution = MockClientHttpRequestExecution()

        // when
        sut.intercept(
            request = givenRequest,
            body = givenBody,
            execution = givenExecution,
        )

        // then
        givenRequest.headers[HttpHeaders.AUTHORIZATION] shouldNotBe null
        givenRequest.headers[HttpHeaders.AUTHORIZATION]!![0] shouldBe givenAccessToken.withPrefix()
        givenExecution.isExecutionMethodCalled() shouldBe true
    }

    @Test
    fun `외부로 나가는 요청을 가로챘는데 저장된 accessToken이 없다면 토큰이 없다는 에러가 발생하고 프로세스가 더이상 진행되지 않는다`() {
        // given
        val givenRequest = MockClientHttpRequest()
        val givenBody = ByteArray(size = 1)
        val givenExecution = MockClientHttpRequestExecution()

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            sut.intercept(
                request = givenRequest,
                body = givenBody,
                execution = givenExecution,
            )
        }

        // then
        givenRequest.headers[HttpHeaders.AUTHORIZATION] shouldBe null
        actualThrownException.errorCode shouldBe ErrorType.NOT_EXIST_TOKEN_ERROR.errorCode
        givenExecution.isExecutionMethodCalled() shouldBe false
    }

    private class MockClientHttpRequestExecution : ClientHttpRequestExecution {
        private var executeMethodCallCount = 0

        fun isExecutionMethodCalled() = executeMethodCallCount > 0

        override fun execute(request: HttpRequest, body: ByteArray): ClientHttpResponse {
            executeMethodCallCount++
            return object : ClientHttpResponse {
                override fun getHeaders() = HttpHeaders()

                override fun getBody() = InputStream.nullInputStream()

                override fun close() {}

                override fun getStatusCode() = HttpStatus.OK

                override fun getRawStatusCode(): Int = HttpStatus.OK.value()

                override fun getStatusText(): String = HttpStatus.OK.name
            }
        }
    }

    private fun accessTokenIsInContext() = AuthUtils.setAccessToken(givenAccessToken)
}
