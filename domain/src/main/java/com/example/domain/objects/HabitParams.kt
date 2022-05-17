package com.example.domain.objects

import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType

class HabitParams(
    val title : String,
    val description : String,
    val priority : Int,
    val type: HabitType,
    val eventsCount : Int,
    val timeIntervalType: TimeIntervalType
) {
}