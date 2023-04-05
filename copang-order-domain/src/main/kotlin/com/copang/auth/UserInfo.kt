package com.copang.auth

import com.copang.common.constants.EMPTY_ID
import com.copang.common.constants.EMPTY_STRING

data class UserInfo(
    val id: Long,
    val userId: String = "",
) {
    companion object {
        private val DUMMY = UserInfo(EMPTY_ID, EMPTY_STRING)

        fun empty(): UserInfo = DUMMY

        fun initOf(id: Long) = UserInfo(id = id)
    }
}
