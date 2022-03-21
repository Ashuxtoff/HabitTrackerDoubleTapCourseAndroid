package com.example.task3.objects

import com.example.task4.R

enum class HabitType(val resId : Int) {
    BAD(R.string.usefulHabitKey),
    USEFUL(R.string.badHabitKey);

    companion object {
        fun from(findValue: Int): HabitType {
            return values().first { it.resId == findValue }

        } // почему-то не совпадают id ресурсов
    }
}