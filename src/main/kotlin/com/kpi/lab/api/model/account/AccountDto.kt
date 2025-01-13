package com.kpi.lab.api.model.account

import com.kpi.lab.persistence.mongo.entity.AccountDocument


class AccountDto(
    val id: String,
    val name: String,
    val balance: Int
)

fun AccountDocument.toDto() =
    AccountDto(
        id = this.id,
        name = this.publicId,
        balance = this.balance
    )