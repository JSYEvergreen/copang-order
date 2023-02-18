package com.copang.product

import com.copang.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "order_product")
class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

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

) : BaseEntity()

enum class ProductStatus {
    CREATED,
    BUYING,
    BUYED,
    REFUNDED,
}
