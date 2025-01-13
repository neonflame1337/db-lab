package com.kpi.lab.persistence.mongo.entity

import com.kpi.lab.utils.UUIDv7
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "users")
class UserDocument(
    @Id val id: String = UUIDv7.generate(),
    @Field("first_name") val firstName: String,
    @Field("last_name") val lastName: String,
    @Field("is_active") var isActive: Boolean = false
) {
    @DBRef(lazy = true)
    var accounts: MutableList<AccountDocument> = mutableListOf()

    @CreatedDate
    @Field("created_at")
    var createdAt: Instant = Instant.now()

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: Instant = Instant.now()

    @Field("deleted_at")
    var deletedAt: Instant? = null

    fun activate() {
        isActive = true
    }

    fun deactivate() {
        isActive = false
    }
}