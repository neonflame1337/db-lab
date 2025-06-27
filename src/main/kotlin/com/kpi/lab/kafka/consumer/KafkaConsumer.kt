package com.kpi.lab.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kpi.lab.api.model.Message
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class KafkaConsumer(@Qualifier("objectMapper") private val objectMapper: ObjectMapper) {

    @KafkaListener(topics = ["pg-employed"], groupId = "my-group")
    fun listen(rawMassage: String) {
        val receivedAt = Instant.now().toEpochMilli()
        val message = objectMapper.readValue<Message>(rawMassage)
        val delay = receivedAt - message.sentAt

        println("Delay $delay ms : $message")
    }
}
