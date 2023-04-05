package com.copang.utils

import com.copang.auth.UserInfo
import com.copang.common.utils.ResponseEntityUtils
import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.ApiResponse
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.ResourceAccessException

class ResponseEntityUtilsTest {
    private val givenClassName = "className"
    private val givenMethodName = "methodName"
    private val givenErrorMessage = "errorMessage"
    private val givenErrorType = ErrorType.AUTH_SERVER_ERROR

    @Test
    fun `200 응답이 아니고 body가 비어있다면 주어진 errorType의 에러를 뱉는다`() {
        // given
        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            ResponseEntity<ApiResponse<Any?>>(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            ResponseEntityUtils.getOrThrows(
                className = givenClassName,
                methodName = givenMethodName,
                errorMessage = givenErrorMessage,
                errorType = givenErrorType,
                apiCall = givenApiCall,
            )
        }

        // then
        actualThrownException.message shouldBe givenErrorType.message
        actualThrownException.errorCode shouldBe givenErrorType.errorCode
    }

    @Test
    fun `200 응답이 아니고 타 서버에서 넘겨준 에러 메시지와 에러코드가 있다면 해당 애러 메시지와 에러코드를 릴레잉한다`() {
        // given
        val givenErrorCode = "0000"
        val givenMessage = "error"
        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            ResponseEntity<ApiResponse<Any?>>(
                ApiResponse.fail(errorCode = givenErrorCode, message = givenMessage),
                HttpStatus.INTERNAL_SERVER_ERROR)
        }

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            ResponseEntityUtils.getOrThrows(
                className = givenClassName,
                methodName = givenMethodName,
                errorMessage = givenErrorMessage,
                errorType = givenErrorType,
                apiCall = givenApiCall,
            )
        }

        // then
        actualThrownException.message shouldBe givenMessage
        actualThrownException.errorCode shouldBe givenErrorCode
    }

    @Test
    fun `200 응답인데 타 서버에서 넘겨준 응답의 body가 null 이라면 주어진 errorType의 에러를 뱉는다`() {
        // given
        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            ResponseEntity<ApiResponse<Any?>>(HttpStatus.OK)
        }

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            ResponseEntityUtils.getOrThrows(
                className = givenClassName,
                methodName = givenMethodName,
                errorMessage = givenErrorMessage,
                errorType = givenErrorType,
                apiCall = givenApiCall,
            )
        }

        // then
        actualThrownException.message shouldBe givenErrorType.message
        actualThrownException.errorCode shouldBe givenErrorType.errorCode
    }

    @Test
    fun `200 응답인데 ApiResponse의 isSuccess가 true라면 주어진 errorType의 에러를 뱉는다`() {
        // given
        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            ResponseEntity<ApiResponse<Any?>>(
                ApiResponse.fail(ErrorType.NOT_EXIST_TOKEN_ERROR),
                HttpStatus.OK
            )
        }

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            ResponseEntityUtils.getOrThrows(
                className = givenClassName,
                methodName = givenMethodName,
                errorMessage = givenErrorMessage,
                errorType = givenErrorType,
                apiCall = givenApiCall,
            )
        }

        // then
        actualThrownException.message shouldBe givenErrorType.message
        actualThrownException.errorCode shouldBe givenErrorType.errorCode
    }

    @Test
    fun `타임 아웃 발생시 주어진 errorType의 에러를 뱉는다`() {
        // given
        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            throw ResourceAccessException("타임아웃 발생")
        }

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            ResponseEntityUtils.getOrThrows(
                className = givenClassName,
                methodName = givenMethodName,
                errorMessage = givenErrorMessage,
                errorType = givenErrorType,
                apiCall = givenApiCall,
            )
        }

        // then
        actualThrownException.message shouldBe givenErrorType.message
        actualThrownException.errorCode shouldBe givenErrorType.errorCode
    }

    @Test
    fun `200 응답이고 isSuccess가 true이면 주어진 응답 content를 뱉는다`() {
        // given
        val givenContent = UserInfo(
            id = 1L,
            userId = "userId",
        )

        val givenApiCall: () -> ResponseEntity<ApiResponse<UserInfo>> = {
            ResponseEntity<ApiResponse<UserInfo>>(
                ApiResponse.success(givenContent),
                HttpStatus.OK
            )
        }

        // when
        val actualContent = ResponseEntityUtils.getOrThrows(
            className = givenClassName,
            methodName = givenMethodName,
            errorMessage = givenErrorMessage,
            errorType = givenErrorType,
            apiCall = givenApiCall,
        )

        // then
        actualContent shouldBe givenContent
    }

    @Test
    fun `200 응답이고 isSuccess가 true이면 주어진 응답 content를 뱉는데, content는 null 일 수 있다`() {
        // given
        val givenContent = null

        val givenApiCall: () -> ResponseEntity<ApiResponse<Any?>> = {
            ResponseEntity<ApiResponse<Any?>>(
                ApiResponse.success(givenContent),
                HttpStatus.OK
            )
        }

        // when
        val actualContent = ResponseEntityUtils.getOrThrows(
            className = givenClassName,
            methodName = givenMethodName,
            errorMessage = givenErrorMessage,
            errorType = givenErrorType,
            apiCall = givenApiCall,
        )

        // then
        actualContent shouldBe givenContent
    }
}
