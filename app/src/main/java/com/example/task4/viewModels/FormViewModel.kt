package com.example.task4.viewModels

import androidx.lifecycle.*
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FormViewModel(private val repository: Repository, private val habitId : Long?) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }


    private val editedHabitLD : LiveData<Habit>? = if (habitId != null) {
        repository.getHabitById(habitId)
    } else {
        null
    }

    fun getEditedHabitLiveData() : LiveData<Habit>? {
        return editedHabitLD
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
                    timeIntervalType : TimeIntervalType) = launch {


        if (habitId != null) {

            val habit = editedHabitLD?.value
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