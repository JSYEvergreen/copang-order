package com.copang.common.interceptor

import com.copang.auth.AccessToken
import com.copang.auth.AuthReader
import com.copang.auth.UserInfo
import com.copang.common.utils.AuthUtils
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 외부에서 들어오는 요청의 accessToken 검증
 */
class InRequestAuthInterceptor(
    private val authReader: AuthReader,
) : HandlerInterceptor {

    private val log = KotlinLogging.logger {}

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val accessToken = AccessToken(
            tokenWithPrefix = request.getHeader(HttpHeaders.AUTHORIZATION),
        )
        AuthUtils.setAccessToken(accessToken)

        val userInfo: UserInfo = authReader.readOrThrows(accessToken.original())
        AuthUtils.setUserInfo(userInfo)

        log.info { "accessToken 검증 완료(accessToken=$accessToken, userInfo=$userInfo)" }
        return true
    }
}
