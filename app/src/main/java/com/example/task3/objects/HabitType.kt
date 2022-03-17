package com.example.task3.objects

enum class HabitType(val value : String) {
    BAD("bad"),
    NEUTRAL("neutral"),
    USEFUL("useful");

    companion object {
        fun from(findValue: String): HabitType = values().first { it.value == findValue }
    }
}