package com.copang.product

interface ProductRepository {
    fun getProductsByIdsIn(productIds: List<Long>): List<Product>
}
