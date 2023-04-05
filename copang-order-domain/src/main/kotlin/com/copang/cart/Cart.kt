package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.product.Product
import java.time.LocalDateTime

data class Cart(
    val id: Long,
    val buyerInfo: UserInfo = UserInfo.empty(),
    val product: Product = Product.empty(),
    val status: CartStatus = CartStatus.ACTIVE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun filledOf(buyerInfo: UserInfo, product: Product) = copy(buyerInfo = buyerInfo, product = product)
}

enum class CartStatus {
    ACTIVE,
    DONE,
    DELETED,
}