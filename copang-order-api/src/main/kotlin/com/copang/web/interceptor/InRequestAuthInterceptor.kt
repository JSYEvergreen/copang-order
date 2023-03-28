package com.copang.web.interceptor

import com.copang.auth.AuthRepository
import com.copang.auth.UserInfo
import com.copang.utils.AuthUtils
import com.copang.auth.AccessToken
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 외부에서 들어오는 요청의 accessToken 검증
 */
class InRequestAuthInterceptor(
    private val authRepository: AuthRepository,
) : HandlerInterceptor {

    private val log = KotlinLogging.logger {}

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val accessToken = AccessToken(
            tokenWithPrefix = request.getHeader(HttpHeaders.AUTHORIZATION),
        )
        val userInfo: UserInfo = authRepository.getUserInfoByAccessTokenOrThrows(accessToken.original())
        AuthUtils.setAccessToken(accessToken)
        AuthUtils.setUserInfo(userInfo)
        log.info { "accessToken 검증 완료(accessToken=$accessToken, userInfo=$userInfo)" }
        return true
    }
}
