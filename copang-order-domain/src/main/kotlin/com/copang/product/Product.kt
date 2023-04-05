package com.copang.product

import com.copang.auth.UserInfo
import com.copang.common.constants.EMPTY_ID
import com.copang.common.constants.EMPTY_NUMBER
import com.copang.common.constants.EMPTY_STRING

data class Product(
    val id: Long,
    val name: String,
    val code: String,
    val description: String,
    val information: String,
    val quantity: Int,
    val cost: Int,
    val sellerInfo: UserInfo,
) {
    companion object {
        private val DUMMY = Product(
            id = EMPTY_ID,
            name = EMPTY_STRING,
            code = EMPTY_STRING,
            description = EMPTY_STRING,
            information = EMPTY_STRING,
            quantity = EMPTY_NUMBER,
            cost = EMPTY_NUMBER,
            sellerInfo = UserInfo.empty()
        )

        fun empty(): Product = DUMMY

        fun initOf(id: Long, quantity: Int) = Product(
            id = id,
            name = EMPTY_STRING,
            code = EMPTY_STRING,
            description = EMPTY_STRING,
            information = EMPTY_STRING,
            quantity = quantity,
            cost = EMPTY_NUMBER,
            sellerInfo = UserInfo.empty()
        )
    }

    fun quantityOf(quantity: Int) = copy(quantity = quantity)
}
