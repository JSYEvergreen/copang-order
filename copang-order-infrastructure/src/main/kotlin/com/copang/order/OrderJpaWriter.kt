package com.copang.order

import com.copang.product.ProductEntity
import com.copang.product.ProductStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class OrderJpaWriter(
    private val repository: OrderJpaRepository,
) : OrderWriter {

    @Transactional
    override fun createOrder(order: Order) {
        val orderEntity = OrderEntity(
            orderCode = order.orderCode,
            buyerId = order.buyerInfo.id,
            products = order.products.map {
                ProductEntity(
                    code = it.code,
                    cost = it.cost,
                    name = it.name,
                    description = it.description,
                    information = it.information,
                    quantity = it.quantity,
                    sellerId = it.sellerInfo.id,
                    status = ProductStatus.CREATED,
                )
            }.toMutableList(),
        )

        repository.save(orderEntity)
    }
}
