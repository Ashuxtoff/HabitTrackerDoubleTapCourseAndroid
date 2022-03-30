package com.example.task4.model

import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType

object Model {

    //private
    val habitsList = mutableListOf<Habit>(
        Habit("1st", "1st", 6, HabitType.BAD, 1, TimeIntervalType.DAYS),
        Habit("2nd", "2nd", 4, HabitType.BAD, 2, TimeIntervalType.HOURS),
        Habit("3rd", "3rd", 3, HabitType.BAD, 3, TimeIntervalType.WEEKS),
        Habit("4th", "4th", 4, HabitType.USEFUL, 4, TimeIntervalType.MONTHS),
        Habit("5th", "5th", 1, HabitType.USEFUL, 5, TimeIntervalType.MONTHS),
        Habit("6th", "6th", 10, HabitType.USEFUL, 6, TimeIntervalType.MONTHS),
        Habit("7th", "7th", 3, HabitType.USEFUL, 7, TimeIntervalType.WEEKS),
        Habit("8th", "8th", 7, HabitType.USEFUL, 8, TimeIntervalType.DAYS)
    )

    fun getAllHabits() : List<Habit> {
        return habitsList
    }


    fun getUsefulHabits() : List<Habit> {
        return habitsList.filter { it.type == HabitType.USEFUL }
    }

    fun getBadHabits() : List<Habit> {
        return habitsList.filter { it.type == HabitType.BAD }
    }

    fun addHabit(habit : Habit) {
        habitsList.add(habit)
    }

    fun editHabit(editedHabit: Habit) {
        val habit = habitsList.find { it.uniqueId == editedHabit.uniqueId}
        editedHabit.apply {
            habit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
        }
    }

}