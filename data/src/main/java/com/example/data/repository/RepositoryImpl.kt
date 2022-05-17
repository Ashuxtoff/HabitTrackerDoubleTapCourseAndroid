package com.example.data.repository


import android.content.Context
import com.example.task3.objects.Habit
import com.example.task4.objects.HabitUID
import com.example.task4.service.HabitsService
import android.util.Log
import com.example.data.databaseObjects.databaseObjects.AppDatabase
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class  RepositoryImpl(context: Context) : Repository {

    companion object {
        private const val EMPTY_STRING = ""
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
    }

    private val db = AppDatabase.getInstance(context)
    private val habitDao = db.habitDao()
    private val service : HabitsService = HabitsService.getInstance()


    override suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>> { // возвращать result от habit
        var newHabitsFlow = service.getHabits() // это все в try
        habitDao.deleteAllHabits()
        habitDao.insertHabits(newHabitsFlow.first())
        if (sortingMode == ASCENDING_SORTING_MODE) {
            newHabitsFlow.map { list -> list.sortedBy { it.priority } }
        }
        if (sortingMode == DESCENDING_SORTING_MODE) {
            newHabitsFlow.map { list -> list.sortedByDescending { it.priority } }
        }

        newHabitsFlow = newHabitsFlow.map { list -> list.filter { habit ->
            habit.type.resId == typeResId && habit.title.contains(searchQuery) } }

        //Log.d("Habits:", newHabitsFlow.toString())
        return newHabitsFlow // а тут еще catch
    }


    override suspend fun getHabitById(uuid : String) : Flow<Habit> {
        return habitDao.getHabitById(uuid)
    }

    override suspend fun putHabit(habit : Habit) : Habit {
        val habitUid : HabitUID = service.putHabit(habit)
        if (habit.uniqueId == EMPTY_STRING) {
            habit.uniqueId = habitUid.uid
            habitDao.addHabit(habit)
        }
        else
        {
            habitDao.editHabit(habit)
        }

        return habit
    }

    override suspend fun doHabit(habit: Habit): Flow<Habit> {
        TODO("Not yet implemented")
    }
}

