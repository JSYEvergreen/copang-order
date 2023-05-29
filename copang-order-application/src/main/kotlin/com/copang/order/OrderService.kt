package com.copang.order

import com.copang.auth.UserInfo
import com.copang.cart.Cart
import com.copang.cart.CartRepository
import com.copang.cart.CartService
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val cartService: CartService,
    private val orderRepository: OrderRepository,
) {
    fun createOrder(buyer: UserInfo): String {
        val carts: List<Cart> = cartService.getAllCarts()
        carts.createOrders()

        val order = Order.empty().filledOf(
            orderCode = UUID.randomUUID().toString(),
            buyerInfo = buyer,
            products = carts.map { it.product },
        )
        orderRepository.createOrder(order)
        return order.orderCode
    }

    private fun List<Cart>.createOrders() = forEach {
        cartService.createOrder(it)
    }
}
