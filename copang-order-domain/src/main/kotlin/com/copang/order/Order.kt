package com.copang.order

import com.copang.auth.UserInfo
import com.copang.common.constants.EMPTY_ID
import com.copang.common.constants.EMPTY_STRING
import com.copang.product.Product

data class Order(
    val id: Long,
    val orderCode: String,
    val buyerInfo: UserInfo,
    val products: List<Product>,
) {
    fun filledOf(orderCode: String, buyerInfo: UserInfo, products: List<Product>) = copy(
        orderCode = orderCode,
        buyerInfo = buyerInfo,
        products = products.map { it.copy() },
    )

    companion object {
        private val DUMMY = Order(
            id = EMPTY_ID,
            orderCode = EMPTY_STRING,
            buyerInfo = UserInfo.empty(),
            products = emptyList(),
        )

        fun empty(): Order = DUMMY
    }
}
