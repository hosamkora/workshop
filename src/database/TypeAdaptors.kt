package com.example.database

import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import org.joda.time.DateTime
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.reflect.Type


class DateTimeTypeAdapter : JsonSerializer<DateTime?>, JsonDeserializer<DateTime?> {
    override fun serialize(datetime: DateTime?, type: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(datetime?.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")))
    }

    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): DateTime? {
        println("deserializing")
        return DateTime.parse(json?.asString, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
    }
}
