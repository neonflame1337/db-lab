package com.kpi.lab.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.kpi.lab.api.model.Message
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Qualifier("objectMapper") private val objectMapper: ObjectMapper
) {
    fun sendMessage(topic: String, payload: String) {
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(Message(payload)))
    }
}