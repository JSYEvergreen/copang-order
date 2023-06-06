package com.copang.web.order

import com.copang.common.utils.ApiResponse
import com.copang.common.utils.AuthUtils
import com.copang.order.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/order/api/order/create")
    fun createOrder(): ApiResponse<OrderCreateResponse> {
        val orderCode = orderService.createOrder(
            buyer = AuthUtils.getUserInfo()
        )
        return ApiResponse.success(
            content = OrderCreateResponse(orderCode)
        )
    }
}
