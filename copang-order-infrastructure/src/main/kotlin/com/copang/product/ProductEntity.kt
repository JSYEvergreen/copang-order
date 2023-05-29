package com.copang.product

import java.time.LocalDateTime
import javax.persistence.*

@Embeddable
class ProductEntity(

    @Column(name = "code")
    val code: String,

    @Column(name = "cost")
    val cost: Int,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "information")
    val information: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "seller_id")
    val sellerId: Long,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: ProductStatus,

) {
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
