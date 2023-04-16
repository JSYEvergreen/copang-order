package com.copang.auth

interface AuthRepository {
    fun getUserInfoByAccessTokenOrThrows(accessToken: String): UserInfo
}
