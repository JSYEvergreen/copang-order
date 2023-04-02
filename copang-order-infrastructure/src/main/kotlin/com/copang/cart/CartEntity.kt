package com.copang.cart

import com.copang.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cart")
class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "product_quantity")
    val quantity: Int,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: CartStatus,

    @Column(name = "buyer_id")
    val buyerId: Long,

    @Column(name = "product_id")
    val productId: Long,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null,
) : BaseEntity()
