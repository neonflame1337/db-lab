package com.kpi.lab.persistence.postgres.repository

import com.kpi.lab.persistence.postgres.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository: JpaRepository<TransactionEntity, UUID> {

    @Query(
        """
            select t from TransactionEntity t
            left join fetch t.fromAccount as fa
            left join fetch t.toAccount as ta
            left join fetch fa.user fu
            left join fetch ta.user tu
            where fu.id = :userId or tu.id = :userId
            order by t.createdAt desc
        """
    )
    fun findUserTransactions(userId: UUID): Collection<TransactionEntity>
}