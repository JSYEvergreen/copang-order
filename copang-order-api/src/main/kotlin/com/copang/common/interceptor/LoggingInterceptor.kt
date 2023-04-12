package com.copang.common.interceptor

import com.copang.common.utils.JsonUtils.toPrettyJsonString
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.InputStream
import java.nio.charset.StandardCharsets

class LoggingInterceptor : ClientHttpRequestInterceptor {
    private val logger = KotlinLogging.logger {}

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        val startTime = System.currentTimeMillis()
        val response = execution.execute(request, body)
        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - startTime

        val httpLogFormat = HttpLogFormat(
            uri = "${request.method} ${request.uri} ${response.statusCode} -> $elapsedTime ms",
            requestHeaders = request.headers,
            requestBody = String(body, Charsets.UTF_8),
            responseHeaders = response.headers,
            responseBody = getResponseBody(response.body)
        )

        logger.info { """
            |
            |${httpLogFormat.toPrettyJsonString()}
        """.trimMargin() }

        return response
    }

    private fun getResponseBody(responseBodyStream: InputStream): String {
        val byteArray = responseBodyStream.readAllBytes()
        return if (byteArray.isNotEmpty()) {
            String(byteArray, StandardCharsets.UTF_8)
        } else {
            ""
        }
    }

    private data class HttpLogFormat(
        val uri: String,
        val requestHeaders: HttpHeaders,
        val requestBody: String,
        val responseHeaders: HttpHeaders,
        val responseBody: String,
    )
}
