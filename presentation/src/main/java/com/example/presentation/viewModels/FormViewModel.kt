package com.example.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryImpl
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import kotlinx.coroutines.*

class FormViewModel(private val repository: RepositoryImpl, private val uuid : String) : ViewModel(){

    companion object {
        private const val EMPTY_STRING = ""
    }

//    private var habitLiveData : LiveData<Habit>? = null
//
//    init {
//        if (uuid != EMPTY_STRING) {
//            viewModelScope.launch (Dispatchers.IO) {
//                habitLiveData = repository.getHabitById(uuid)
//            }
//        }
//    }

    private var habit : Habit? = null

    init {
        if (uuid != EMPTY_STRING) {
            viewModelScope.launch (Dispatchers.IO) {
                habit = repository.getHabitById(uuid)
            }
        }
    }


    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalType : TimeIntervalType) = viewModelScope.launch (Dispatchers.IO) {

        if (uuid != EMPTY_STRING) {
            habit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
            if (habit != null) {
                repository.putHabit(habit!!)
            }
        }
        else {
            repository.putHabit(
                Habit(title, description, priority, type, eventsCount, timeIntervalType)
            )
        }
    }
}





//if (uniqueId != null) {
//
//
//            val habit = repository.getHabitById(uniqueId).value
//            if (habit != null) {
//                habit.edit(title, description, priority, type, eventsCount, timeIntervalType)
//                repository.editHabit(habit)
//            }
//        }
//
//        else {
//            addHabit(title, description, priority, type, eventsCount, timeIntervalType)
//        }