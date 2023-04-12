package com.copang.common.config

import com.copang.common.interceptor.LoggingInterceptor
import com.copang.common.interceptor.OutRequestAuthInterceptor
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig(
    private val restTemplateCustomizers: Set<RestTemplateCustomizer>,
) {
    @Bean
    fun copangRestTemplate(): RestTemplate = RestTemplateBuilder()
        .interceptors(OutRequestAuthInterceptor(), LoggingInterceptor())
        .setReadTimeout(Duration.ofMillis(1_100))
        .setConnectTimeout(Duration.ofMillis(1_100))
        .customizers(restTemplateCustomizers)
        .requestFactory { BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory()) }
        .build()
}
