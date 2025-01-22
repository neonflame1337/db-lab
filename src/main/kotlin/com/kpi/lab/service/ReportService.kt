package com.kpi.lab.service

import com.kpi.lab.api.model.reports.UserTransactionDetail
import com.kpi.lab.api.model.reports.UserTransactionsSummary
import com.kpi.lab.api.model.reports.toUserTransactionDetail
import com.kpi.lab.api.model.reports.toUserTransactionsSummary
import com.kpi.lab.persistence.postgres.entity.AccountEntity
import com.kpi.lab.persistence.postgres.repository.AccountRepository
import com.kpi.lab.exception.EntityNotFoundException
import com.kpi.lab.exception.InvalidOperationException
import com.kpi.lab.persistence.postgres.repository.TransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReportService(
    private val transactionRepository: TransactionRepository,
    private val userService: UserService
) {
    fun getUserDetailReport(userId: UUID): List<UserTransactionDetail> {
        userService.getById(userId)
        return transactionRepository.findUserTransactions(userId).map { it.toUserTransactionDetail(userId) }
    }

    fun getUserSummaryReport(userId: UUID): UserTransactionsSummary {
        userService.getById(userId)
        return transactionRepository.findUserTransactions(userId).toUserTransactionsSummary(userId)
    }
}