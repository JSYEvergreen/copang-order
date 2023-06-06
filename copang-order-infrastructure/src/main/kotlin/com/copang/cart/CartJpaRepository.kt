package com.copang.cart

import org.springframework.data.jpa.repository.JpaRepository

internal interface CartJpaRepository : JpaRepository<CartEntity, Long> {
    fun findAllByBuyerIdAndStatusIn(buyerId: Long, statuses: List<CartStatus>): List<CartEntity>
    fun findByBuyerIdAndProductIdAndStatusIn(buyerId: Long, productId: Long, statuses: List<CartStatus>): CartEntity?
}
