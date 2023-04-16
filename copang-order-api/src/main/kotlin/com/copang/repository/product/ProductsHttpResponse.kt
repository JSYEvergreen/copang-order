package com.copang.repository.product

import com.copang.auth.UserInfo
import com.copang.product.Product

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
) {
    fun toDomain() = Product(
        id = productId,
        name = productName,
        code = productCode,
        description = productDescription,
        information = productInformation,
        quantity = productQuantity,
        cost = productCost,
        sellerInfo = UserInfo.initOf(productSellerId),
    )
}
