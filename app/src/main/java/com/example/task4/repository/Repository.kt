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

    private val habits : LiveData<List<Habit>> = habitDao.getAllHabits()

    fun getAllHabits() : LiveData<List<Habit>> {
        return habitDao.getAllHabits()
    }

    fun getUsefulHabits() : LiveData<List<Habit>> {
        return habitDao.getUsefulHabits()
    }

    fun getBadHabits() : LiveData<List<Habit>> {
        return habitDao.getBadHabits()
    }

    fun getHabitById(uuid : String) : LiveData<Habit> {
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






















//private
//    val habitsList = mutableListOf<Habit>(
//        Habit("1st", "1st", 6, HabitType.BAD, 1, TimeIntervalType.DAYS),
//        Habit("2nd", "2nd", 4, HabitType.BAD, 2, TimeIntervalType.HOURS),
//        Habit("3rd", "3rd", 3, HabitType.BAD, 3, TimeIntervalType.WEEKS),
//        Habit("4th", "4th", 4, HabitType.USEFUL, 4, TimeIntervalType.MONTHS),
//        Habit("5th", "5th", 1, HabitType.USEFUL, 5, TimeIntervalType.MONTHS),
//        Habit("6th", "6th", 10, HabitType.USEFUL, 6, TimeIntervalType.MONTHS),
//        Habit("7th", "7th", 3, HabitType.USEFUL, 7, TimeIntervalType.WEEKS),
//        Habit("8th", "8th", 7, HabitType.USEFUL, 8, TimeIntervalType.DAYS)
//    )
//
//
//    fun getAllHabits() : List<Habit> {
//        return habitsList
//    }
//
//
//    fun getUsefulHabits() : List<Habit> {
//        return habitsList.filter { it.type == HabitType.USEFUL }
//    }
//
//    fun getBadHabits() : List<Habit> {
//        return habitsList.filter { it.type == HabitType.BAD }
//    }
//
//    fun addHabit(habit : Habit) {
//        habitsList.add(habit)
//    }
//
//    fun editHabit(editedHabit: Habit) {
//        val habit = habitsList.find { it.uniqueId == editedHabit.uniqueId}
//        editedHabit.apply {
//            habit?.edit(
//                title, description, priority, type, eventsCount, timeIntervalType
//            )
//        }
//    }