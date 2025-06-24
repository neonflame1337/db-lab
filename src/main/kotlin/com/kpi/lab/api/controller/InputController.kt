package com.kpi.lab.api.controller

import com.kpi.lab.api.model.Message
import com.kpi.lab.kafka.producer.KafkaProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class InputController(
    val producer: KafkaProducer
) {
    @PostMapping("input")
    fun input(@RequestBody message: Message): String {
        producer.sendMessage("lab1", message.message ?: "null")

        return "message \"${message.message}\" was sent"
    }
}