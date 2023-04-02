package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.utils.AuthUtils
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
) {
    fun getAllCarts(): List<Cart> {
        val buyerInfo: UserInfo = AuthUtils.getUserInfo()
        val initialCart = cartRepository.getAllActiveByBuyerId(buyerId = buyerInfo.id)
        if (initialCart.isEmpty()) {
            return listOf(Cart.emptyOf(buyerInfo))
        }

        // product 가져오기, 채우기
    }
}
