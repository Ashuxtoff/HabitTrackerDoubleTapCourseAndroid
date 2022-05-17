package com.example.domain.repository

import com.example.task3.objects.Habit
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>>

    suspend fun getHabitById(uuid : String) : Flow<Habit>

    suspend fun putHabit(habit : Habit) : Habit

    suspend fun doHabit(habit : Habit) : Habit
}