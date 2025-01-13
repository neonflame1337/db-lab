package com.kpi.lab.configuration.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class InstantDeserializer : JsonDeserializer<Instant>() {

    private val dateTimeFormatters = listOf<DateTimeFormatter>(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
    )

    override fun deserialize(parser: JsonParser?, ctxt: DeserializationContext?): Instant {
        for (formatter in dateTimeFormatters) {
            try {
                return Instant.from(formatter.withZone(ZoneOffset.UTC).parse(parser?.text))
            } catch (e: DateTimeParseException) {
            }
        }
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(parser?.text))
    }
}
