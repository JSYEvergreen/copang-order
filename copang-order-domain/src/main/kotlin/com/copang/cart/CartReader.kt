package com.copang.cart

interface CartReader {
    fun getAllActiveByBuyerId(buyerId: Long): List<Cart>
    fun getActiveCartByBuyerIdAndProductId(buyerId: Long, productId: Long): Cart
    fun getActiveByIdAndBuyerIdOrThrows(cartId: Long, buyerId: Long): Cart
}
