package com.kpi.lab.service

import com.kpi.lab.exception.EntityNotFoundException
import com.kpi.lab.exception.InvalidOperationException
import com.kpi.lab.persistence.mongo.entity.AccountDocument
import com.kpi.lab.persistence.mongo.repository.AccountRepository
import org.springframework.stereotype.Service


@Service
class AccountService(
    private val userService: UserService,
    private val accountRepository: AccountRepository,
) {
    fun getById(id: String) =
        accountRepository.findActiveById(id) ?: throw EntityNotFoundException("account with id: $id was not found")

    fun create(userId: String, accountName: String, balance: Int): AccountDocument {
        val user = userService.getById(userId)
        val account = accountRepository.save(
            AccountDocument(
                publicId = accountName,
                balance = balance,
            ).also { it.user = user }
        )
        userService.addUserAccount(user, account)
        return account
    }

    fun deposit(id: String, amount: Int) =
        accountRepository.save(getById(id).also { it.balance += amount })

    fun withdraw(id: String, amount: Int): AccountDocument {
        val account = getById(id)
        if ( (account.balance - amount) < 0)
            throw InvalidOperationException("insufficient funds on account with id: $id")
        return accountRepository.save(account.also { it.balance -= amount })
    }
}