package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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

    override fun getActiveCartByBuyerIdAndProductId(buyerId: Long, productId: Long): Cart =
        repository.findByBuyerIdAndProductIdAndStatusIn(
            buyerId = buyerId,
            productId = productId,
            statuses = listOf(CartStatus.ACTIVE),
        )?.let {
            Cart(
                id = it.id!!,
                product = Product.initOf(id = productId, quantity = it.quantity),
                buyerInfo = UserInfo.initOf(id = buyerId)
            )
        } ?: Cart.empty()

    override fun addCart(cart: Cart) {
        val cartEntity = CartEntity(
            productId = cart.product.id,
            buyerId = cart.buyerInfo.id,
        ).update(
            quantity = cart.product.quantity,
            status = CartStatus.ACTIVE,
        )
        repository.save(cartEntity)
    }

    @Transactional
    override fun updateCart(cart: Cart) {
        val cartEntity = repository.findByIdOrNull(cart.id)
            ?: throw CopangException(ErrorType.NOT_EXIST_CART_ERROR)
        cartEntity.update(
            quantity = cart.product.quantity,
            status = cart.status,
        )
    }

    override fun getActiveByIdAndBuyerIdOrThrows(cartId: Long, buyerId: Long): Cart {
        val cartEntity = repository.findByIdOrNull(cartId)
            ?: throw CopangException(ErrorType.NOT_EXIST_CART_ERROR)

        if (cartEntity.status != CartStatus.ACTIVE || cartEntity.buyerId != buyerId) {
            throw CopangException(errorType = ErrorType.NOT_EXIST_CART_ERROR)
        }
        return Cart(
            id = cartEntity.id!!,
            buyerInfo = UserInfo.initOf(cartEntity.buyerId),
            product = Product.initOf(id = cartEntity.productId, quantity = cartEntity.quantity),
            createdAt = cartEntity.createdAt,
            updatedAt = cartEntity.updatedAt,
        )
    }
}

internal interface CartInnerJpaRepository : JpaRepository<CartEntity, Long> {
    fun findAllByBuyerIdAndStatusIn(buyerId: Long, statuses: List<CartStatus>): List<CartEntity>
    fun findByBuyerIdAndProductIdAndStatusIn(buyerId: Long, productId: Long, statuses: List<CartStatus>): CartEntity?
}
