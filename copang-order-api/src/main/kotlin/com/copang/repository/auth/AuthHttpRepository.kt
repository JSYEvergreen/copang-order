package com.copang.repository.auth

import com.copang.auth.AuthRepository
import com.copang.auth.UserInfo
import com.copang.common.ApiResponse
import com.copang.common.exception.ErrorType
import com.copang.common.utils.ResponseEntityUtils
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
                        "$authServerUrl/login",
                        HttpMethod.GET,
                        null,
                    )
                }
            )
        )
}
