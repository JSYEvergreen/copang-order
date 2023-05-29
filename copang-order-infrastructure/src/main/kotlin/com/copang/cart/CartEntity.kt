package com.copang.cart

import com.copang.common.BaseEntity
import com.copang.common.utils.TimeUtils
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cart")
class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "buyer_id")
    val buyerId: Long,

    @Column(name = "product_id")
    val productId: Long,
) : BaseEntity() {
    @Column(name = "product_quantity")
    var quantity: Int = 0
        protected set

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: CartStatus = CartStatus.ACTIVE
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    fun update(quantity: Int = this.quantity, status: CartStatus): CartEntity {
        this.quantity = quantity
        this.status = status
        return this
    }

    fun delete() {
        this.deletedAt = TimeUtils.now()
        this.status = CartStatus.DELETED
    }
}
