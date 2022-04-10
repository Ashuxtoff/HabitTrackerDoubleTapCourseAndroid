package com.example.task4.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.databaseObjects.AppDatabase

class Repository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val habitDao = db.habitDao()


    fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : LiveData<List<Habit>> {
        return habitDao.getCurrentHabits(typeResId, sortingMode, searchQuery)
    }

    fun getHabitById(uuid : Long) : LiveData<Habit> {
        return habitDao.getHabitById(uuid)
    }

    fun addHabit(habit : Habit) {
        habitDao.addHabit(habit)
    }

    fun editHabit(habit : Habit) {
        habitDao.editHabit(habit)
    }



//  не забыть потом почистить комментарий со старым кодом снизу



}

















//    fun getAllHabits() : LiveData<List<Habit>> {
//        return habitDao.getAllHabits()
//    }
//
//    fun getUsefulHabits() : LiveData<List<Habit>> {
//        return habitDao.getUsefulHabits()
//    }
//
//    fun getBadHabits() : LiveData<List<Habit>> {
//        return habitDao.getBadHabits()
//    }