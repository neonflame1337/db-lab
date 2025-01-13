package com.kpi.lab.persistence.postgres.repository

import com.kpi.lab.persistence.postgres.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository: JpaRepository<AccountEntity, UUID>