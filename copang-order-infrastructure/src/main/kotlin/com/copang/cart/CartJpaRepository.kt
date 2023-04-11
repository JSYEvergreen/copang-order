package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CartJpaRepository(
    private val repository: CartInnerJpaRepository,
) : CartRepository {
    override fun getAllActiveByBuyerId(buyerId: Long): List<Cart> {
        return repository.findAllByBuyerIdAndStatusIn(
            buyerId = buyerId,
            statuses = listOf(CartStatus.ACTIVE)
        ).map {
            Cart(
                id = it.id!!,
                buyerInfo = UserInfo.initOf(buyerId),
                product = Product.initOf(id = it.productId, quantity = it.quantity),
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }
}

internal interface CartInnerJpaRepository : JpaRepository<CartEntity, Long> {
    fun findAllByBuyerIdAndStatusIn(buyerId: Long, statuses: List<CartStatus>): List<CartEntity>
}
