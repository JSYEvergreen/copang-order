package com.copang.web.cart

import com.copang.auth.UserInfo
import com.copang.cart.Cart
import com.copang.cart.CartService
import com.copang.common.ApiResponse
import com.copang.common.utils.AuthUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CartController(
    private val cartService: CartService,
){
    @GetMapping("/order/api/carts/buyer")
    fun getAllCarts(): ApiResponse<AllCartsResponse> =
        ApiResponse.success(
            content = cartService.getAllCarts().toResponse()
        )

    @PostMapping("/order/api/add-cart")
    fun addCart(
        @Valid @RequestBody request: AddCartRequest,
    ): ApiResponse<Any?> {
        cartService.addCart(
            buyer = AuthUtils.getUserInfo(),
            productId = request.productId!!,
            quantity = request.quantity!!,
        )
        return ApiResponse.success()
    }
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
            cartId = it.id,
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
