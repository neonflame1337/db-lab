package com.kpi.lab.service

import com.kpi.lab.persistence.postgres.entity.TransactionEntity
import com.kpi.lab.persistence.postgres.entity.TransactionStatus
import com.kpi.lab.persistence.postgres.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountService: AccountService,
    postgresTransactionManager: PlatformTransactionManager
) {
    private val transactionTemplate = TransactionTemplate(postgresTransactionManager)

    fun transfer(
        fromAccountId: UUID,
        toAccountId: UUID,
        amount: Int,
    ) {
        val fromAccount = accountService.getById(fromAccountId)
        val toAccount = accountService.getById(toAccountId)

        val transaction = transactionRepository.save(
            TransactionEntity(
                amount = amount,
                status = TransactionStatus.CREATED
            ).also {
                it.fromAccount = fromAccount
                it.toAccount = toAccount
                it.initiatorUser = fromAccount.user
            }
        )

        runCatching {
            transactionTemplate.execute {
                accountService.withdraw(fromAccountId, amount)
                accountService.deposit(toAccountId, amount)

                transaction.status = TransactionStatus.SUCCESS
                transactionRepository.save(transaction)
            }
        }.onFailure {
            transaction.status = TransactionStatus.FAILED
            transactionRepository.save(transaction)
            throw it
        }
    }
}