package com.example.task4.viewModels

import androidx.lifecycle.*
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.repository.Repository

class FormViewModel(private val repository: Repository, private val habitId : Long?) : ViewModel() {

    private val editedHabitLD : LiveData<Habit>? = if (habitId != null) {
        repository.getHabitById(habitId)
    } else {
        null
    }

    fun getEditedHabitLiveData() : LiveData<Habit>? {
        return editedHabitLD
    }


//    private fun getEditedHabitLD(uniqueId: Long) {
//        editedHabitLD = repository.getHabitById(uniqueId)
//    }
//
//    private val editedHabitTransformation = Transformations.switchMap(editedHabitLD,
//        { habit -> MutableLiveData(habit)}
//    )

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
                    timeIntervalType : TimeIntervalType) {


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