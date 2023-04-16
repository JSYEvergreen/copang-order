package com.copang.cart

interface CartRepository {
    fun getAllActiveByBuyerId(buyerId: Long): List<Cart>
    fun addCart(cart: Cart)
    fun getActiveCartByBuyerIdAndProductId(buyerId: Long, productId: Long): Cart
    fun updateCart(cart: Cart)
}
