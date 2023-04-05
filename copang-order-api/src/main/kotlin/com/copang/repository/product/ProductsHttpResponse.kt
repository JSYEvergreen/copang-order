package com.copang.repository.product

data class ProductsHttpResponse(
    val content: List<ProductsBodyHttpResponse>,
)

data class ProductsBodyHttpResponse(
    val productId: Long,
    val productCode: String,
    val productName: String,
    val productDescription: String,
    val productInformation: String,
    val productQuantity: Int,
    val productCost: Int,
    val productIsSale: Boolean,
    val productSellerId: Long,
)
