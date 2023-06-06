package com.copang.web.interceptor

import com.copang.auth.AuthReader
import com.copang.auth.UserInfo
import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.interceptor.InRequestAuthInterceptor
import com.copang.common.utils.AuthUtils
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class InRequestAuthInterceptorTest {
    private val authReader = mockk<AuthReader>()
    private val sut = InRequestAuthInterceptor(
        authReader,
    )
    private val givenAccessToken = "token"
    private val givenExistUserInfo = UserInfo(
        id = 1L,
        userId = "userId",
    )

    @AfterEach
    fun tearDown() {
        AuthUtils.clearUserInfo()
    }

    @Test
    fun `요청 Authorization 헤더의 accessToken이 검증되면 컨텍스트에 토큰과 유저 정보가 저장되고 통과한다`() {
        // given
        val givenRequest = MockHttpServletRequest().withAccessToken(givenAccessToken)

        val givenResponse = MockHttpServletResponse()
        val givenHandler = "handler"
        every { authReader.readOrThrows(accessToken = any()) } returns givenExistUserInfo

        // when
        val actualResult: Boolean = sut.preHandle(
            request = givenRequest,
            response = givenResponse,
            handler = givenHandler,
        )
        val actualSavedAccessToken = AuthUtils.getAccessToken().original()
        val actualUserInfo = AuthUtils.getUserInfo()

        // then
        verify(exactly = 1) { authReader.readOrThrows(accessToken = any()) }
        actualResult shouldBe true
        actualSavedAccessToken shouldBe givenAccessToken
        actualUserInfo shouldBe givenExistUserInfo
    }

    @Test
    fun `요청 Authorization 헤더에 accessToken이 없으면 토큰이 없다는 에러가 발생한다`() {
        // given
        val givenRequest = MockHttpServletRequest().withOutAccessToken()

        val givenResponse = MockHttpServletResponse()
        val givenHandler = "handler"

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            sut.preHandle(
                request = givenRequest,
                response = givenResponse,
                handler = givenHandler,
            )
        }

        // then
        verify(exactly = 0) { authReader.readOrThrows(accessToken = any()) }
        actualThrownException.errorCode shouldBe ErrorType.NOT_EXIST_TOKEN_ERROR.errorCode
    }

    @Test
    fun `요청 Authorization 헤더의 accessToken이 검증되지 않으면 auth 서버의 유저 정보 조회 에러를 릴레잉해서 던진다`() {
        // given
        val givenRequest = MockHttpServletRequest().withAccessToken(givenAccessToken)

        val givenResponse = MockHttpServletResponse()
        val givenHandler = "handler"
        every {
            authReader.readOrThrows(accessToken = any())
        } throws CopangException(ErrorType.AUTH_SERVER_ERROR)

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            sut.preHandle(
                request = givenRequest,
                response = givenResponse,
                handler = givenHandler,
            )
        }

        // then
        verify(exactly = 1) { authReader.readOrThrows(accessToken = any()) }
        actualThrownException.errorCode shouldBe ErrorType.AUTH_SERVER_ERROR.errorCode
    }

    private fun MockHttpServletRequest.withAccessToken(token: String) =
        apply { addHeader(HttpHeaders.AUTHORIZATION, token) }

    private fun MockHttpServletRequest.withOutAccessToken() = this
}
