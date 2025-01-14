package com.kpi.lab.persistence.mongo.entity

import com.fasterxml.jackson.annotation.JsonValue
import com.kpi.lab.utils.UUIDv7
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "transactions")
open class TransactionDocument(
    @Id val id: String = UUIDv7.generate(),
    @Field("status") var status: TransactionStatus = TransactionStatus.CREATED,
    @Field("amount") val amount: Int,
    @Field("metadata") val metadata: String? = null
) {
    @CreatedDate
    @Field("created_at")
    var createdAt: Instant = Instant.now()

    @DBRef(lazy = true)
    @Field("initiator_user_id")
    lateinit var initiatorUser: UserDocument

    @DBRef(lazy = true)
    @Field("from_account_id")
    lateinit var fromAccount: AccountDocument

    @DBRef(lazy = true)
    @Field("to_account_id")
    lateinit var toAccount: AccountDocument
}

enum class TransactionStatus(private val status: String) {
    CREATED("created"),
    PROCESSING("processing"),
    VERIFYING("verifying"),
    SUCCESS("success"),
    FAILED("failed");

    @JsonValue
    override fun toString(): String = status

    companion object {
        private val values = entries.associateBy { it.status }
        fun orNull(text: String): TransactionStatus? = values[text]
    }
}
