package com.example.task4.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FormViewModel(private val repository: Repository, private val uuid : String?) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    private var habitLiveData : LiveData<Habit> = MutableLiveData()

    init {
        if (uuid != null) {
            launch {
                val deferredHabit: Deferred<LiveData<Habit>> = async {
                    repository.getHabitById(uuid)
                }

                habitLiveData = deferredHabit.await()
            }
    }

    }


    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalType : TimeIntervalType) = launch {

        if (uuid != null) {
            val habit = habitLiveData.value
            habit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
            if (habit != null) {
                repository.putHabit(habit)
            }
        }
        else{
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