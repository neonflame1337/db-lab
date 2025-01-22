package com.kpi.lab.api.model.reports

import com.kpi.lab.persistence.postgres.entity.TransactionEntity
import com.kpi.lab.persistence.postgres.entity.TransactionStatus
import java.time.Instant
import java.util.UUID

class UserTransactionsSummary(
    val incomeAmount: Int,
    val outcomeAmount: Int,
    val lastIncome: Instant?,
    val lastOutcome: Instant?,
    val transactionsIncomeCount: Int,
    val transactionsOutcomeCount: Int,
    val transactionsTotalCount: Int
)

fun Collection<TransactionEntity>.toUserTransactionsSummary(userId: UUID): UserTransactionsSummary {
    val transactionsDetails = map { it.toUserTransactionDetail(userId) }
    val incomeTransactions = transactionsDetails.filter { it.type == TransactionType.INCOME }
    val outcomeTransactions = transactionsDetails.filter { it.type == TransactionType.OUTCOME }

    return UserTransactionsSummary(
        incomeAmount = incomeTransactions.filter { it.status == TransactionStatus.SUCCESS }.sumOf { it.amount },
        outcomeAmount = outcomeTransactions.filter { it.status == TransactionStatus.SUCCESS }.sumOf { it.amount },
        lastIncome = incomeTransactions.maxOfOrNull { it.transactedAt },
        lastOutcome = outcomeTransactions.maxOfOrNull { it.transactedAt },
        transactionsIncomeCount = incomeTransactions.size,
        transactionsOutcomeCount = outcomeTransactions.size,
        transactionsTotalCount = transactionsDetails.size,
    )
}