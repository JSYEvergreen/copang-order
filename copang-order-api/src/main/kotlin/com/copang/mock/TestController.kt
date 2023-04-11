package com.copang.mock

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/order/ping")
    fun get() = "pong"

    @PostMapping("/order/ping")
    fun post(@RequestBody body: TestRequestClass): String {
        return body.body
    }

    data class TestRequestClass(
        val body: String,
    )
}
