package com.copang.common.config

import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    companion object {
        private const val DEFAULT_TIMEOUT = 5_000L
    }

    @Bean
    fun copangWebClient(): WebClient {
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(
                HttpClient.create()
                    .responseTimeout(Duration.ofMillis(DEFAULT_TIMEOUT))
                    .doOnConnected { connection ->
                        connection
                            .addHandlerLast(ReadTimeoutHandler(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS))
                            .addHandlerLast(WriteTimeoutHandler(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS))
                    }
            ))
            .build()
    }
}
