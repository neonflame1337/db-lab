package com.kpi.lab.configuration.persistance

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:application.yaml")
@EnableJpaRepositories(
    basePackages = ["com.kpi.lab.persistence.postgres.repository"],
    entityManagerFactoryRef = "postgresEntityManager",
    transactionManagerRef = "postgresTransactionManager"
)
class PostgresDatabaseConfiguration : AbstractDatabaseConfiguration() {

    @Value("\${app.persistence.postgres.jdbc-url}")
    private lateinit var connectionUrl: String

    @Value("\${app.persistence.postgres.pool-size}")
    private var poolSize: Int = DEFAULT_POOL_SIZE

    @Bean
    fun postgresDataSource(): DataSource = datasource(
        connectionUrl,
        poolSize,
        "postgresDatabaseConnectionsPool"
    )

    @Bean
    fun postgresEntityManager(): LocalContainerEntityManagerFactoryBean {
        return entityManager(
            postgresDataSource(),
            "com.kpi.lab.persistence.postgres.entity"
        )
    }

    @Bean
    fun postgresTransactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = postgresEntityManager().getObject()
        return transactionManager
    }
}
