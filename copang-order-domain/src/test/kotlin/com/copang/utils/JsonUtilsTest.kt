package com.copang.utils

import com.copang.auth.UserInfo
import com.copang.utils.JsonUtils.toJsonOrNull
import com.copang.utils.JsonUtils.toJsonString
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class JsonUtilsTest {
    private val testData = UserInfo(
        id = 1L,
        userId = "userId",
    )
    private val testDataJsonString = "{\"id\":1,\"userId\":\"userId\"}"

    @Test
    fun `toJsonOrNull 메소드 실행시 json string을 객체로 변환할 수 있다`() {
        val userInfo: UserInfo? = testDataJsonString.toJsonOrNull<UserInfo>()

        userInfo shouldNotBe null
        userInfo shouldBe testData
    }

    @Test
    fun `toJsonString() 메소드 실행시 객체를 json string으로 변환할 수 있다`() {
        val jsonString = testData.toJsonString()

        jsonString shouldBe testDataJsonString
    }
}
