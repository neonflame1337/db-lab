package com.kpi.lab.persistence.mongo.repository

import com.kpi.lab.persistence.mongo.entity.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<UserDocument, String> {
    @Query("{ 'deleted_at': null }")
    fun findAllActive(): MutableList<UserDocument>

    @Query("{ '_id': ?0, 'deleted_at': null }")
    fun findActiveById(id: String): UserDocument?
}