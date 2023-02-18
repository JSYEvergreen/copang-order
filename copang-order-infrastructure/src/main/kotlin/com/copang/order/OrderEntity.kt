package com.copang.order

import com.copang.BaseEntity
import com.copang.product.ProductEntity
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

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "order_history_id")
    val products: MutableList<ProductEntity> = mutableListOf()

) : BaseEntity()
