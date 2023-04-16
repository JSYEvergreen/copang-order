package com.copang.common.config

import com.copang.common.CommonResponseErrorHandler
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class CommonRestTemplateCustomizer : RestTemplateCustomizer {
    override fun customize(restTemplate: RestTemplate?) {
        restTemplate?.errorHandler = CommonResponseErrorHandler()
    }
}
