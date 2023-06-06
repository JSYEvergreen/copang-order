package com.copang.product

interface ProductReader {
    fun readAllIn(productIds: List<Long>): List<Product>
}
