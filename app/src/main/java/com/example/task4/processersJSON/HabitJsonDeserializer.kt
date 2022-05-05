package com.example.task4.processersJSON

import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HabitJsonDeserializer : JsonDeserializer<Habit> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Habit =
        Habit(
            json.asJsonObject.get("title").asString,
            json.asJsonObject.get("description").asString,
            json.asJsonObject.get("priority").asInt,

            when(json.asJsonObject.get("type").asInt) {
                0 -> HabitType.USEFUL
                else -> HabitType.BAD
            },

            json.asJsonObject.get("frequency").asInt,
            TimeIntervalType.WEEKS
        )
}