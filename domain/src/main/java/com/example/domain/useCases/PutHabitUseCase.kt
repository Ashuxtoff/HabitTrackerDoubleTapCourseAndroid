package com.example.domain.useCases

import com.example.domain.objects.HabitParams
import com.example.domain.repository.Repository
import com.example.task3.objects.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PutHabitUseCase(private val repository: Repository) {

    suspend fun execute(params : HabitParams): Habit {
        val habit = Habit(
            params.title,
            params.description,
            params.priority,
            params.type,
            params.eventsCount,
            params.timeIntervalType
        )

        return withContext(Dispatchers.IO) { repository.putHabit(habit) }
    }
}