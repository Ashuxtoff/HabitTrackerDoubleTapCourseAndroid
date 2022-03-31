package com.example.task4.viewModels

import androidx.lifecycle.ViewModel
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.model.Model

class FormViewModel(private val model: Model) : ViewModel() {

    private fun addHabit(title : String,
                 description : String,
                 priority : Int,
                 type : HabitType,
                 eventsCount : Int,
                 timeIntervalType : TimeIntervalType,) {

        model.addHabit(Habit(
            title, description, priority, type, eventsCount, timeIntervalType
        ))
    }

    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalType : TimeIntervalType,
                    uniqueId : String?) {

        if (uniqueId != null) {
            val editedHabit = model.getAllHabits().find { it.uniqueId == uniqueId }
            editedHabit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
        }

        else {
            addHabit(title, description, priority, type, eventsCount, timeIntervalType)
        }
    }
}