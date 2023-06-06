package com.copang.common.config

import com.copang.auth.AuthReader
import com.copang.common.interceptor.InRequestAuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authReader: AuthReader,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(InRequestAuthInterceptor(authReader))
    }
}
