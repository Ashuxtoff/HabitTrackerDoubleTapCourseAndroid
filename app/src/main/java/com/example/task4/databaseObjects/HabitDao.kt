package com.example.task4.databaseObjects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.task3.objects.Habit
import com.example.task4.R

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE habit_type_res_id = ${R.string.usefulHabitKey}")
    fun getUsefulHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE habit_type_res_id = ${R.string.badHabitKey}")
    fun getBadHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :uuid")
    fun getHabitById(uuid : String) : LiveData<Habit>

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun editHabit(habit: Habit)
}