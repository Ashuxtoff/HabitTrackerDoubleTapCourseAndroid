package com.example.task4.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FormViewModel(private val repository: Repository, private val uuid : String) : ViewModel(){

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