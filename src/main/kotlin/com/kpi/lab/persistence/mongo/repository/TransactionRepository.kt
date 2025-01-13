package com.kpi.lab.persistence.mongo.repository

import com.kpi.lab.persistence.mongo.entity.TransactionDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: MongoRepository<TransactionDocument, String>