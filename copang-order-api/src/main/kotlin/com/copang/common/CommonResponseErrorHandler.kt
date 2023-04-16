package com.copang.common

import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class CommonResponseErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean =
        response.statusCode.isError

    override fun handleError(response: ClientHttpResponse) {}
}
