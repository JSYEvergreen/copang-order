package com.copang.order

import org.springframework.data.jpa.repository.JpaRepository

internal interface OrderJpaRepository : JpaRepository<OrderEntity, Long>
