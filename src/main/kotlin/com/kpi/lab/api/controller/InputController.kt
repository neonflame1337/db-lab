package com.kpi.lab.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kpi.lab.api.model.Message
import com.kpi.lab.kafka.producer.KafkaProducer
import com.kpi.lab.persistence.postgres.entity.EmployedEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
@RequestMapping("api/v1")
class InputController(
    val producer: KafkaProducer,
    val objectMapper: ObjectMapper,
) {
    @PostMapping("input")
    fun input(@RequestBody message: Message): String {
        producer.sendMessage("lab1", message.message ?: "null")

        return "message \"${message.message}\" was sent"
    }

    @GetMapping("generate")
    fun generate(): String {
        val n = 10

        (1..n).map {
            producer.sendMessage(
                "lab2",
                objectMapper.writeValueAsString(
                    EmployedEntity(
                        id = BigInteger.valueOf(it.toLong()),
                        industry = "TEST-$it",
                        majorOccupation = "TEST-$it",
                        minorOccupation = "TEST-$it",
                        raceGender = "TEST-$it",
                        industryTotal = it.toDouble(),
                        employN = it.toDouble(),
                        year = 2025 + it
                    )
                )
            )
        }

        return "messages $n were sent"
    }
}