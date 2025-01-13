package com.kpi.lab.api.model.account

import java.util.UUID

class CreateAccountRequest (
    val userId: UUID,
    val name: String,
    val balance: Int
)