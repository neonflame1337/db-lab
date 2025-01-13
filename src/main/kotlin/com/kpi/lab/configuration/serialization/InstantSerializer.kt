package com.kpi.lab.configuration.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.kpi.lab.configuration.serialization.SerializationConfiguration.Companion.defaultDateTimeFormatter
import java.time.Instant

class InstantSerializer : JsonSerializer<Instant>() {

    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider?) {
        val string = defaultDateTimeFormatter.format(value)
        gen.writeString(string)
    }
}
