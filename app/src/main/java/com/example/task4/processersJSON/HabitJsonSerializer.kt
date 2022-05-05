package com.example.task4.processersJSON

import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.JsonObject
import java.lang.reflect.Type

class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(src: Habit, typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
        JsonObject().apply {
            addProperty("color", 0)
            addProperty("count", 0)
            addProperty("date", 0)
            addProperty("description", src.description)
            //add("done_dates", JsonObject().asJsonArray)
            addProperty("frequency", src.eventsCount) // тут не очень конечно получается
            addProperty("priority", src.priority)
            addProperty("title", src.title)
            addProperty("type", when(src.type) {
                HabitType.USEFUL -> 0
                HabitType.BAD -> 1
            })

            if (src.uniqueId != null) {
                addProperty("uid", src.uniqueId)
            }
        }
}