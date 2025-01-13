package com.kpi.lab.persistence.postgres.entity

import com.fasterxml.jackson.annotation.JsonValue
import com.kpi.lab.utils.UUIDv7
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    val id: UUID = UUIDv7.generate(),
    @Convert(converter = TransactionStatusConverter::class)
    var status: TransactionStatus = TransactionStatus.CREATED,
    val amount: Int,
    val metadata: String? = null
) {
    @CreationTimestamp
    val createdAt: Instant = Instant.now()

    @ManyToOne
    @JoinColumn(name = "initiator_user_id")
    lateinit var initiatorUser: UserEntity

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    lateinit var fromAccount: AccountEntity

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    lateinit var toAccount: AccountEntity
}

enum class TransactionStatus(private val status: String) {
    CREATED("created"),
    PROCESSING("processing"),
    VERIFYING("verifing"),
    SUCCESS("success"),
    FAILED("failed");

    @JsonValue
    override fun toString(): String = status

    companion object {
        private val values = entries.associateBy { it.status }
        fun orNull(text: String) = values[text]
    }
}

@Converter
class TransactionStatusConverter : AttributeConverter<TransactionStatus, String> {
    override fun convertToDatabaseColumn(attribute: TransactionStatus): String = attribute.toString()
    override fun convertToEntityAttribute(dbData: String?): TransactionStatus? =
        dbData?.let { TransactionStatus.orNull(dbData) }
}