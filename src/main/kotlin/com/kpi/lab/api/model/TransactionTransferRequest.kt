package com.kpi.lab.api.model

import java.util.UUID

class TransactionTransferRequest(
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: Int
)