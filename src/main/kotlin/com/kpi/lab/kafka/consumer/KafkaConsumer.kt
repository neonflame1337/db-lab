package com.kpi.lab.kafka.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["employed-topic"], groupId = "my-group")
    fun listen(message: String) {
        println("Received: $message")
    }
}
