package com.copang.mock

import com.copang.auth.UserInfo
import com.copang.cart.Cart
import com.copang.cart.CartStatus
import com.copang.product.Product
import java.time.LocalDateTime

object CartTestData {
    fun cart(
        id: Long,
        buyerInfo: UserInfo = UserInfoTestData.userInfo(),
        product: Product = ProductTestData.product(),
        status: CartStatus = CartStatus.ACTIVE,
        createdAt: LocalDateTime? = null,
        updatedAt: LocalDateTime? = null,
        deletedAt: LocalDateTime? = null,
    ) = Cart(
        id = id,
        buyerInfo = buyerInfo,
        product = product,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}
