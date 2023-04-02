package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.product.Product
import com.copang.product.ProductRepository
import com.copang.utils.AuthUtils
import com.copang.utils.SafeMap
import com.copang.utils.toSafeMap
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    fun getAllCarts(): List<Cart> {
        val buyerInfo: UserInfo = AuthUtils.getUserInfo()
        val initialCart: List<Cart> = cartRepository.getAllActiveByBuyerId(buyerId = buyerInfo.id)
        if (initialCart.isEmpty()) {
            return emptyList()
        }

        val productIds = initialCart.map { it.product.id }
        val productsMap: SafeMap<Long, Product> = productRepository.getProductsByIdsIn(productIds).toSafeMap { it.id }
        return initialCart.map {
            it.filledOf(
                buyerInfo = buyerInfo,
                product = productsMap[it.product.id].quantityOf(quantity = it.product.quantity),
            )
        }
    }
}
