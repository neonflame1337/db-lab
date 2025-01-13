package com.kpi.lab.api.controller

import com.kpi.lab.api.model.TransactionTransferRequest
import com.kpi.lab.service.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/transaction")
class TransactionController(
    private val transactionService: TransactionService
) {
    @PostMapping("/transfer")
    fun transfer(@RequestBody request: TransactionTransferRequest) =
        transactionService.transfer(
            fromAccountId = request.fromAccountId,
            toAccountId = request.toAccountId,
            amount = request.amount
        )
}