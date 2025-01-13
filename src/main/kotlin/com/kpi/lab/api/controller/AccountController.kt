package com.kpi.lab.api.controller

import com.kpi.lab.api.model.account.CreateAccountRequest
import com.kpi.lab.api.model.account.toDto
import com.kpi.lab.service.AccountService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v1/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/create")
    fun createAccount(@RequestBody request: CreateAccountRequest) =
        accountService.create(
            userId = request.userId,
            accountName = request.name,
            balance = request.balance
        ).toDto()

    @PostMapping("/{accountId}/deposit")
    fun deposit(@PathVariable accountId: UUID, amount: Int) =
        accountService.deposit(accountId, amount)

    @PostMapping("/{accountId}/withdraw")
    fun withdraw(@PathVariable accountId: UUID, amount: Int) =
        accountService.withdraw(accountId, amount)

    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: UUID) { accountService.delete(accountId) }
}