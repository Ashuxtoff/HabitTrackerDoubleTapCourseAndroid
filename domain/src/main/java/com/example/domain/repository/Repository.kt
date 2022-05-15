package com.example.domain.repository

import com.example.task3.objects.Habit

interface Repository {

    suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : List<Habit>

    suspend fun getHabitById(uuid : String) : Habit

    suspend fun putHabit(habit : Habit)
}