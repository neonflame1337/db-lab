package com.kpi.lab.persistence.postgres.entity

import com.kpi.lab.utils.UUIDv7
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id val id: UUID = UUIDv7.generate(),
    val firstName: String,
    val lastName: String,
) {
    @OneToMany(mappedBy = "user")
    var accounts: MutableList<AccountEntity> = mutableListOf()

    @CreationTimestamp var createdAt: Instant = Instant.now()
    @UpdateTimestamp var updatedAt: Instant = Instant.now()
    var deletedAt: Instant? = null

    var isActive: Boolean = false
        protected set

    fun activate() { isActive = true }
    fun deactivate() { isActive = false }
}