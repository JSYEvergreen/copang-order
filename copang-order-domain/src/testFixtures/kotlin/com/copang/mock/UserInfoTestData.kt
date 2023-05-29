package com.copang.mock

import com.copang.auth.UserInfo

object UserInfoTestData {
    fun userInfo(
        id: Long = 1L,
        userId: String = "",
    ) = UserInfo(
        id = id,
        userId = userId,
    )
}
