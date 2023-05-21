package com.copang.cart

interface CartRepository {
    fun getAllActiveByBuyerId(buyerId: Long): List<Cart>
    fun getActiveCartByBuyerIdAndProductId(buyerId: Long, productId: Long): Cart
    fun getActiveByIdAndBuyerIdOrThrows(cartId: Long, buyerId: Long): Cart

    fun addCart(cart: Cart)
    fun updateCart(cart: Cart)
    fun deleteCart(cart: Cart)

    fun createOrder(cart: Cart)
}
