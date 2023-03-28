package com.copang.web

import com.copang.auth.AuthRepository
import com.copang.web.interceptor.InRequestAuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authRepository: AuthRepository,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
         registry.addInterceptor(InRequestAuthInterceptor(authRepository))
    }
}
