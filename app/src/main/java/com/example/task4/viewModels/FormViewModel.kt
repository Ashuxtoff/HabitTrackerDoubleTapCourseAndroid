package com.example.task4.viewModels

import androidx.lifecycle.ViewModel
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FormViewModel(private val repository: Repository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    private fun addHabit(title : String,
                         description : String,
                         priority : Int,
                         type : HabitType,
                         eventsCount : Int,
                         timeIntervalType : TimeIntervalType,) {

        repository.addHabit(Habit(
            title, description, priority, type, eventsCount, timeIntervalType
        ))
    }

    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalType : TimeIntervalType,
                    uniqueId : Long?) = launch {

        if (uniqueId != null) {

            val habit = repository.getHabitById(uniqueId).value
            if (habit != null) {
                habit.edit(title, description, priority, type, eventsCount, timeIntervalType)
                repository.editHabit(habit)
            }
        }

        else {
            addHabit(title, description, priority, type, eventsCount, timeIntervalType)
        }
    }
}