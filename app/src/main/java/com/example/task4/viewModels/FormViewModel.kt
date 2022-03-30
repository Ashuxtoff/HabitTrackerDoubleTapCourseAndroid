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
                 timeIntervalTypeId : Int,) {

        model.addHabit(Habit(
            title, description, priority, type, eventsCount, TimeIntervalType.from(timeIntervalTypeId)
        ))
    }

    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalTypeId : Int,
                    uniqueId : String?) {

        if (uniqueId != null) {
            val editedHabit = model.getAllHabits().find { it.uniqueId == uniqueId }
            editedHabit?.edit(
                title, description, priority, type, eventsCount, TimeIntervalType.from(timeIntervalTypeId)
            )
        }

        else {
            addHabit(title, description, priority, type, eventsCount, timeIntervalTypeId)
        }
    }
}