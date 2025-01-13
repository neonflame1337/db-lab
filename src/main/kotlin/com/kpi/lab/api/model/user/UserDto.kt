package com.kpi.lab.api.model.user

import com.fasterxml.jackson.annotation.JsonInclude
import com.kpi.lab.api.model.account.AccountDto
import com.kpi.lab.api.model.account.toDto
import com.kpi.lab.persistence.mongo.entity.UserDocument
import java.time.Instant

class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val accounts: List<AccountDto>? = null
)

fun UserDocument.toDto() = UserDto(
    id = this.id.toString(),
    firstName = this.firstName,
    lastName = this.lastName,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    accounts = this.accounts.map { it.toDto() }
)