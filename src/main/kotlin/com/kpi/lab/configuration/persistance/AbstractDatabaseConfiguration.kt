package com.kpi.lab.configuration.persistance

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.ValidationMode
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

abstract class AbstractDatabaseConfiguration {

    protected fun entityManager(datasource: DataSource, entityPackage: String): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = datasource
            jpaVendorAdapter = HibernateJpaVendorAdapter()
                .apply {
                    setDatabase(Database.POSTGRESQL)
                    setShowSql(false)
                    setGenerateDdl(false)
                    setValidationMode(ValidationMode.NONE)
                }
            setPackagesToScan(entityPackage)
            val properties = mapOf(
                "hibernate.physical_naming_strategy" to CamelCaseToUnderscoresNamingStrategy::class.java.name,
                "hibernate.jdbc.time_zone" to "UTC",
                "hibernate.temp.use_jdbc_metadata_defaults" to false
            )
            jpaPropertyMap.putAll(properties)
        }
    }

    protected fun datasource(connectionUrl: String, poolSize: Int, poolName: String? = null): DataSource =
        HikariConfig().run {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = connectionUrl
            maximumPoolSize = poolSize
            isAutoCommit = true
            poolName?.let { this.poolName = poolName }
            validate()
            return HikariDataSource(this)
        }

    companion object {
        const val DEFAULT_POOL_SIZE = 3
    }
}
