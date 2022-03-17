package com.example.task3.objects

import java.util.*

class Habit(
    var title: String, var description: String, var priority: Int, typeString : String,
    var eventsCount: Int, timeIntervalTypeString : String) {

    var type : HabitType = initializeType(typeString)
    var timeIntervalType : TimeIntervalType = initializeTimeIntervalType(timeIntervalTypeString)
    val uniqueId = UUID.randomUUID().toString()

    private fun initializeType(typeString : String) = HabitType.from(typeString ?: "neutral")

    private fun initializeTimeIntervalType(timeIntervalTypeString : String) = TimeIntervalType.from(timeIntervalTypeString ?: "days")


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
                  newTypeString : String, newEventsCount : Int, newTimeIntervalTypeString : String) {

        title = newTitle
        description = newDescription
        priority = newPriority
        type = initializeType(newTypeString)
        eventsCount = newEventsCount
        timeIntervalType = initializeTimeIntervalType(newTimeIntervalTypeString)
    }


//val colorString = colorString


}