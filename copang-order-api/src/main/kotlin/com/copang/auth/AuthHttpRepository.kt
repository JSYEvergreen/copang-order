package com.copang.auth

import com.copang.exception.ErrorType
import com.copang.utils.ResponseEntityUtils
import com.copang.web.ApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Repository
class AuthHttpRepository(
    private val copangRestTemplate: RestTemplate,
    @Value("\${copang.servers.auth}") private val authServerUrl: String,
) : AuthRepository {

    override fun getUserInfoByAccessTokenOrThrows(accessToken: String): UserInfo =
        requireNotNull(
            ResponseEntityUtils.getOrThrows(
                className = "AuthHttpRepository",
                methodName = "getUserInfoByAccessTokenOrNull",
                errorMessage = "유저 정보 조회 실패.",
                errorType = ErrorType.AUTH_SERVER_ERROR,
                apiCall = {
                    copangRestTemplate.exchange<ApiResponse<UserInfo>>(
                        "${authServerUrl}/buyer/login",
                        HttpMethod.GET,
                        null,
                    )
                }
            )
        )
}
