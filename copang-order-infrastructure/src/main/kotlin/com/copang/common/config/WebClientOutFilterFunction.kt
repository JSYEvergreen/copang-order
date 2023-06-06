package com.copang.common.config

import com.copang.common.utils.AuthUtils
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono

class WebClientOutFilterFunction : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        request.headers()
            .add(HttpHeaders.AUTHORIZATION, AuthUtils.getAccessToken().withPrefix())
        return next.exchange(request)
    }
}
