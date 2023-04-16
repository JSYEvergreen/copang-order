package com.copang.web.cart

data class AllCartsResponse(
    val buyerId: Long,
    val buyerUserId: String,
    val carts: List<AllCartsBodyResponse>,
)

data class AllCartsBodyResponse(
    val productId: Long,
    val productQuantity: Int,
    val productCode: String,
    val productCost: Int,
    val productName: String,
    val productDescription: String,
    val productInformation: String,
    val sellerId: Long,
)
