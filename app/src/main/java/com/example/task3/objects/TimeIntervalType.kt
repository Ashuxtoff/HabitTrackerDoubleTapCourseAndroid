package com.example.task3.objects

enum class TimeIntervalType(val value : String) {
    HOURS("hour"),
    DAYS("day"),
    WEEKS("week"),
    MONTHS("month");


    companion object {
        fun from(findValue: String): TimeIntervalType = values().first { it.value == findValue }
    }
}