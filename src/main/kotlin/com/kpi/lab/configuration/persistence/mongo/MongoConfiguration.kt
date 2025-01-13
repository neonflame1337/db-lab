package com.kpi.lab.configuration.persistence.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.util.Collections


@Configuration
@EnableMongoRepositories(basePackages = ["com.kpi.lab.persistence.mongo.repository"])
class MongoConfiguration: AbstractMongoClientConfiguration() {
    @Value("\${app.persistence.mongo.url}")
    private lateinit var connectionString: String

    override fun getDatabaseName() = "mongo_db"

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(connectionString)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(mongoClientSettings)
    }

    public override fun getMappingBasePackages(): MutableCollection<String> {
        return Collections.singleton("com.kpi.lab.persistence.mongo.entity")
    }
}