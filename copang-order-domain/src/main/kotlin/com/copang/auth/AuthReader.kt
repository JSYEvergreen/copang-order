package com.copang.auth

interface AuthReader {
    fun readOrThrows(accessToken: String): UserInfo
}
