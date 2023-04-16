package com.copang.web.cart

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class AddCartRequest(
    @field:NotNull(message = "productId는 필수값입니다.")
    val productId: Long?,
    @field:Min(value = 1, message = "장바구니에 담을 상품 개수는 최소 1개 이상 담아주세요.")
    @field:NotNull(message = "장바구니에 담을 상품 개수는 필수값입니다.")
    val quantity: Int?,
)
