package com.example.task4.repository


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.task3.objects.Habit
import com.example.task4.databaseObjects.AppDatabase
import com.example.task4.service.HabitsService

class Repository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val habitDao = db.habitDao()
    private val service : HabitsService = HabitsService.getInstance()


    suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : LiveData<List<Habit>> {
        val newHabits = service.getHabits()
        print(newHabits.toString())
        habitDao.deleteAllHabits()
        habitDao.insertHabits(newHabits)
        return habitDao.getCurrentHabits(typeResId, sortingMode, searchQuery)
    }

    suspend fun getHabitById(uuid : String) : LiveData<Habit> {
        val newHabits = service.getHabits()
        habitDao.deleteAllHabits()
        habitDao.insertHabits(newHabits)
        return habitDao.getHabitById(uuid)
    }

//    suspend fun addHabit(habit : Habit) {
//        service.putHabit(habit)
//        habitDao.addHabit(habit)
//    }
//
//    suspend fun editHabit(habit : Habit) {
//        service.putHabit(habit)
//        habitDao.editHabit(habit)
//    }

    suspend fun putHabit(habit : Habit) {
        service.putHabit(habit) // тут прописать логикуна получение HabitUID
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