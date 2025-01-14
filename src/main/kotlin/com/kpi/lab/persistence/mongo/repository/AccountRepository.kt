package com.kpi.lab.persistence.mongo.repository

import com.kpi.lab.persistence.mongo.entity.AccountDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: MongoRepository<AccountDocument, String> {
    @Query("{ 'deleted_at': null }")
    fun findAllActive(): MutableList<AccountDocument>

    @Query("{ '_id': ?0, 'deleted_at': null }")
    fun findActiveById(id: String): AccountDocument?
}