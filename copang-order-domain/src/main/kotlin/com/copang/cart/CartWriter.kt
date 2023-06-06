package com.copang.cart

interface CartWriter {
    fun addCart(cart: Cart)
    fun updateCart(cart: Cart)
    fun deleteCart(cart: Cart)
    fun createOrder(cart: Cart)
}
