package com.kpi.lab.api.model

import java.time.Instant

data class Message(
    val message: String,
    val sentAt: Long = Instant.now().toEpochMilli()
)