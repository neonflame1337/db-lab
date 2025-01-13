package com.kpi.lab.configuration.serialization

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Configuration
class SerializationConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(Instant::class.java, InstantSerializer())
        javaTimeModule.addDeserializer(Instant::class.java, InstantDeserializer())
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        objectMapper.registerModules(KotlinModule(), javaTimeModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }

    companion object {
        val defaultDateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)
    }
}
