package com.kpi.lab.persistence.postgres.repository

import com.kpi.lab.persistence.postgres.entity.EmployedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface EmployedRepository: JpaRepository<EmployedEntity, BigInteger>