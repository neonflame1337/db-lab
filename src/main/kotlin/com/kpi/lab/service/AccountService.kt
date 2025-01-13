package com.kpi.lab.service

import com.kpi.lab.persistence.postgres.entity.AccountEntity
import com.kpi.lab.persistence.postgres.repository.AccountRepository
import com.kpi.lab.exception.EntityNotFoundException
import com.kpi.lab.exception.InvalidOperationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(
    private val userService: UserService,
    private val accountRepository: AccountRepository,
) {
    fun getById(id: UUID) =
        accountRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("account with id: $id was not found")

    fun create(userId: UUID, accountName: String, balance: Int) =
        accountRepository.save(
            AccountEntity(
                publicId = accountName,
                balance = balance,
            ).also { it.user = userService.getById(userId) }
        )

    fun deposit(id: UUID, amount: Int) =
        accountRepository.save(getById(id).also { it.balance += amount })

    fun withdraw(id: UUID, amount: Int): AccountEntity {
        val account = getById(id)
        if ( (account.balance - amount) < 0)
            throw InvalidOperationException("insufficient funds on account with id: $id")
        return accountRepository.save(account.also { it.balance -= amount })
    }
}