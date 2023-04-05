package com.copang.mock

import com.copang.auth.UserInfo
import com.copang.common.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class MockAuthServerController {
    @GetMapping("/buyer/login")
    fun login(
        request: HttpServletRequest,
    ): ApiResponse<UserInfo> {
        val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader.isNullOrBlank()) {
            return ApiResponse.fail(
                errorCode = "10001",
                message = "올바른 토큰이 아닙니다.",
            )
        }
        return ApiResponse.success(
            content = UserInfo(
                id = 1L,
                userId = "testUserId",
            )
        )
    }
}
