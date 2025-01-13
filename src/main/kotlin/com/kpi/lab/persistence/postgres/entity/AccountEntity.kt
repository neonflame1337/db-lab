package com.kpi.lab.persistence.postgres.entity

import com.kpi.lab.utils.UUIDv7
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    val id: UUID = UUIDv7.generate(),
    val publicId: String,
    var balance: Int,
) {
    @CreationTimestamp
    lateinit var createdAt: Instant
    @UpdateTimestamp
    lateinit var updatedAt: Instant
    var deletedAt: Instant? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: UserEntity
}