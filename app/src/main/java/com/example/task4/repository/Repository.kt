package com.example.task4.repository


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.task3.objects.Habit
import com.example.task4.databaseObjects.AppDatabase
import com.example.task4.objects.HabitUID
import com.example.task4.service.HabitsService
import android.util.Log

class Repository(context: Context) {

    companion object {
        private const val EMPTY_STRING = ""
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
    }

    private val db = AppDatabase.getInstance(context)
    private val habitDao = db.habitDao()
    private val service : HabitsService = HabitsService.getInstance()


    suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : List<Habit> { // возвращать result от habit
        var newHabits = service.getHabits() // это все в try
        habitDao.deleteAllHabits()
        habitDao.insertHabits(newHabits)
        if (sortingMode == ASCENDING_SORTING_MODE) {
            newHabits = newHabits.sortedBy { it.priority }
        }
        if (sortingMode == DESCENDING_SORTING_MODE) {
            newHabits = newHabits.sortedByDescending { it.priority }
        }

        newHabits = newHabits.filter { habit ->
            habit.type.resId == typeResId && habit.title.contains(searchQuery)
        }
        Log.d("Habits:", newHabits.toString())
        return newHabits // а тут еще catch
    }


    suspend fun getHabitById(uuid : String) : Habit {
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
        val habitUid : HabitUID = service.putHabit(habit)
        if (habit.uniqueId == EMPTY_STRING) {
            habit.uniqueId = habitUid.uid
            habitDao.addHabit(habit)
        }
        else
        {
            habitDao.editHabit(habit)
        }
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