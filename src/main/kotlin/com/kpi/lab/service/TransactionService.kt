package com.kpi.lab.service

import com.kpi.lab.persistence.mongo.entity.TransactionDocument
import com.kpi.lab.persistence.mongo.entity.TransactionStatus
import com.kpi.lab.persistence.mongo.repository.TransactionRepository
import org.springframework.stereotype.Service


@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountService: AccountService,
) {

    fun transfer(
        fromAccountId: String,
        toAccountId: String,
        amount: Int,
    ) {
        val fromAccount = accountService.getById(fromAccountId)
        val toAccount = accountService.getById(toAccountId)

        val transaction = transactionRepository.save(
            TransactionDocument(
                amount = amount,
                status = TransactionStatus.CREATED
            ).also {
                it.fromAccount = fromAccount
                it.toAccount = toAccount
                it.initiatorUser = fromAccount.user
            }
        )

        runCatching {
            accountService.withdraw(fromAccountId, amount)
            accountService.deposit(toAccountId, amount)

            transaction.status = TransactionStatus.SUCCESS
            transactionRepository.save(transaction)
        }.onFailure {
            transaction.status = TransactionStatus.FAILED
            transactionRepository.save(transaction)
            throw it
        }
    }
}