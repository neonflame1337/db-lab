package com.kpi.lab.api.model



class TransactionTransferRequest(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Int
)