package com.example.task3.objects

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Habit(
    var title: String, var description: String, var priority: Int, var type : HabitType, // можно сразу предавать тип
    var eventsCount: Int, var timeIntervalType : TimeIntervalType) : Parcelable {

    val uniqueId = UUID.randomUUID().toString()

    //val colorString = colorString


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
             newType : HabitType, newEventsCount : Int, newTimeIntervalType : TimeIntervalType) {

        title = newTitle
        description = newDescription
        priority = newPriority
        type = newType
        eventsCount = newEventsCount
        timeIntervalType = newTimeIntervalType
    }
}