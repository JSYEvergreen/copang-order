package com.copang.common.interceptor

import com.copang.common.utils.AuthUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

/**
 * 외부로 내보내는 요청 헤더에 accessToken 추가
 */
class OutRequestAuthInterceptor : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        request.headers.add(HttpHeaders.AUTHORIZATION, AuthUtils.getAccessToken().withPrefix())
        return execution.execute(request, body)
    }
}
