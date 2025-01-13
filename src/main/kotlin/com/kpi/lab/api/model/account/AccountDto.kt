package com.kpi.lab.api.model.account

import com.kpi.lab.persistence.postgres.entity.AccountEntity
import java.util.UUID

class AccountDto(
    val id: UUID,
    val name: String,
    val balance: Int
)

fun AccountEntity.toDto() =
    AccountDto(
        id = this.id,
        name = this.publicId,
        balance = this.balance
    )