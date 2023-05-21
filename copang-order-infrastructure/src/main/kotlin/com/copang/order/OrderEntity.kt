package com.copang.order

import com.copang.common.utils.TimeUtils
import com.copang.product.ProductEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "order_history")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "order_code")
    val orderCode: String,

    @Column(name = "buyer_id")
    val buyerId: Long,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_product", joinColumns = [JoinColumn(name = "order_history_id")])
    @OrderColumn(name = "id")
    val products: MutableList<ProductEntity> = mutableListOf()

) {
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null
        protected set

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
        protected set

    @PrePersist
    fun prePersist() {
        val now: LocalDateTime = TimeUtils.now()
        createdAt = now
        updatedAt = now
        products.forEach {
            it.createdAt = now
            it.updatedAt = now
        }
    }

    @PreUpdate
    fun preUpdate() {
        val now: LocalDateTime = TimeUtils.now()
        updatedAt = now
        products.forEach { it.updatedAt = now }
    }
}
