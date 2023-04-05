package com.copang.web.cart

import com.copang.auth.UserInfo
import com.copang.cart.Cart
import com.copang.cart.CartService
import com.copang.common.utils.AuthUtils
import com.copang.common.ApiResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CartController(
    private val cartService: CartService,
){
    @GetMapping("/api/carts/buyer")
    fun getAllCarts(): ApiResponse<AllCartsResponse> =
        ApiResponse.success(
            content = cartService.getAllCarts().toResponse()
        )
}

private fun List<Cart>.toResponse(): AllCartsResponse {
    val buyerInfo: UserInfo = AuthUtils.getUserInfo()
    if (isEmpty()) {
        return AllCartsResponse(
            buyerId = buyerInfo.id,
            buyerUserId = buyerInfo.userId,
            carts = emptyList(),
        )
    }
    val cartBodyResponse: List<AllCartsBodyResponse> = map {
        AllCartsBodyResponse(
            productId = it.product.id,
            productQuantity = it.product.quantity,
            productCode = it.product.code,
            productCost = it.product.cost,
            productName = it.product.name,
            productDescription = it.product.description,
            productInformation = it.product.information,
            sellerId = it.product.sellerInfo.id,
        )
    }
    return AllCartsResponse(
        buyerId = buyerInfo.id,
        buyerUserId = buyerInfo.userId,
        carts = cartBodyResponse,
    )
}
