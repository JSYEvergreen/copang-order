package com.copang.order

interface OrderRepository {
    fun createOrder(order: Order)
}
