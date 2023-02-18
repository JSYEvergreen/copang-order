package com.copang

import com.copang.utils.TimeUtils
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
class BaseEntity {
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
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = TimeUtils.now()
    }
}
