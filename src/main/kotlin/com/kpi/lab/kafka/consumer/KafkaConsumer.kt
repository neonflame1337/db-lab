package com.kpi.lab.kafka.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["lab1"], groupId = "my-group")
    fun listen(message: String) {
        println("Received: $message")
    }
}
