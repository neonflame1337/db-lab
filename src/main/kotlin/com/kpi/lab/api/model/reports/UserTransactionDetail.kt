package com.kpi.lab.api.model.reports

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue
import com.kpi.lab.persistence.postgres.entity.AccountEntity
import com.kpi.lab.persistence.postgres.entity.TransactionEntity
import com.kpi.lab.persistence.postgres.entity.TransactionStatus
import com.kpi.lab.persistence.postgres.entity.UserEntity
import java.time.Instant
import java.util.UUID

class UserTransactionDetail(
    val amount: Int,
    val status: TransactionStatus,
    val transactedAt: Instant,
    val type: TransactionType,
    val account: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val fromUser: String?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val toUser: String?,
)

enum class TransactionType(private val value: String) {
    INCOME("income"),
    OUTCOME("outcome");
    @JsonValue
    override fun toString(): String = value
}
 fun TransactionEntity.toUserTransactionDetail(userId: UUID): UserTransactionDetail {
     val transactionType = if (userId == fromAccount.user.id) TransactionType.OUTCOME else TransactionType.INCOME
     lateinit var primaryAccount: AccountEntity
     lateinit var secondaryAccount: AccountEntity
     if (transactionType == TransactionType.INCOME) {
         primaryAccount = toAccount
         secondaryAccount = fromAccount
     } else {
         primaryAccount = fromAccount
         secondaryAccount = toAccount
     }

     return UserTransactionDetail(
         amount = amount,
         status = status,
         transactedAt = createdAt,
         type = transactionType,
         account = primaryAccount.publicId,
         fromUser = if (transactionType == TransactionType.INCOME) secondaryAccount.user.formatUserName() else null,
         toUser = if (transactionType == TransactionType.OUTCOME) secondaryAccount.user.formatUserName() else null,
     )
 }

private fun UserEntity.formatUserName() = "${firstName.lowercase().capitalize()} ${lastName.first().uppercase()}."