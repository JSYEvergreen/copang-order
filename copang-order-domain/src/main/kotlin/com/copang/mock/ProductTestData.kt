package com.copang.mock

import com.copang.auth.UserInfo
import com.copang.product.Product

object ProductTestData {
    fun product(
        id: Long = 1L,
        name: String = "name",
        code: String = "code",
        description: String = "description",
        information: String = "information",
        quantity: Int = 1,
        cost: Int = 1_000,
        sellerInfo: UserInfo = UserInfoTestData.userInfo(),
    ) = Product(
        id = id,
        name = name,
        code = code,
        description = description,
        information = information,
        quantity = quantity,
        cost = cost,
        sellerInfo = sellerInfo,
    )
}
