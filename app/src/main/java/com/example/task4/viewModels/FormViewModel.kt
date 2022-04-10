package com.example.task4.viewModels

import androidx.lifecycle.ViewModel
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository

class FormViewModel(private val repository: Repository) : ViewModel() {

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
                    uniqueId : Long?) {

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