package com.example.task3.objects

import java.util.*

class Habit(
    var title: String, var description: String, var priority: Int, var type : HabitType, // можно сразу предавать тип
    var eventsCount: Int, var timeIntervalType : TimeIntervalType) {

    val uniqueId = UUID.randomUUID().toString()


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
                  newType : HabitType, newEventsCount : Int, newTimeIntervalType : TimeIntervalType) {

        title = newTitle
        description = newDescription
        priority = newPriority
        type = newType
        eventsCount = newEventsCount
        timeIntervalType = newTimeIntervalType
    }


//val colorString = colorString


}