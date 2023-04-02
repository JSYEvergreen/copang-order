package com.copang.cart

interface CartRepository {
    fun getAllActiveByBuyerId(buyerId: Long): List<Cart>
}
