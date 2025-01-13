package com.kpi.lab.persistence.mongo.entity

import com.kpi.lab.utils.UUIDv7
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "accounts")
class AccountDocument(
    @Id val id: String = UUIDv7.generate(),
    @Field("public_id") val publicId: String,
    @Field("balance") var balance: Int
) {
    @CreatedDate
    @Field("created_at")
    var createdAt: Instant = Instant.now()

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: Instant = Instant.now()

    @Field("deleted_at")
    var deletedAt: Instant? = null

    @DBRef(lazy = true)
    @Field("user_id")
    lateinit var user: UserDocument
}