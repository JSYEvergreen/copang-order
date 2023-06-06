package com.copang.cart

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class CartJpaWriter(
    private val repository: CartJpaRepository,
) : CartWriter {
    /**
     * TODO : CUD에서 응답값으로 Cart 내보내도록 하기
     */
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

    @Transactional
    override fun deleteCart(cart: Cart) {
        val cartEntity = repository.findByIdOrNull(cart.id)
            ?: throw CopangException(ErrorType.NOT_EXIST_CART_ERROR)
        cartEntity.delete()
    }

    @Transactional
    override fun createOrder(cart: Cart) {
        val cartEntity = repository.findByIdOrNull(cart.id)
            ?: throw CopangException(ErrorType.NOT_EXIST_CART_ERROR)
        cartEntity.update(status = CartStatus.DONE)
    }
}
