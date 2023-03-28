package com.copang.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {

    }
}
