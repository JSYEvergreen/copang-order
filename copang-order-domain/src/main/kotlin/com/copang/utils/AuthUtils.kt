package com.copang.utils

import com.copang.auth.AccessToken
import com.copang.auth.UserInfo
import com.copang.utils.JsonUtils.toJsonString
import org.slf4j.MDC

private const val ACCESS_TOKEN = "accessToken"
private const val USER_INFO = "userInfo"

object AuthUtils {
    fun setAccessToken(accessToken: AccessToken) = MDC.put(ACCESS_TOKEN, accessToken.original())
    fun getAccessToken(): AccessToken = AccessToken(MDC.get(ACCESS_TOKEN))
    fun clearAccessToken() = MDC.remove(ACCESS_TOKEN)

    fun setUserInfo(userInfo: UserInfo) = MDC.put(USER_INFO, userInfo.toJsonString())
}
