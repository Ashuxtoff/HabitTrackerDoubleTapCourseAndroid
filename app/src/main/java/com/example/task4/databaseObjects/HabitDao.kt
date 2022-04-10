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

//    companion object {
//        private const val ASCENDING_SORTING_MODE = "ascending"
//        private const val DESCENDING_SORTING_MODE = "descending"
//        private const val EMPTY_STRING = ""
//    }

//    @Query("SELECT * FROM habits")
//    fun getAllHabits(): LiveData<List<Habit>>
//
//    @Query("SELECT * FROM habits WHERE habit_type_res_id = ${R.string.usefulHabitKey}")
//    fun getUsefulHabits(): LiveData<List<Habit>>
//
//    @Query("SELECT * FROM habits WHERE habit_type_res_id = ${R.string.badHabitKey}")
//    fun getBadHabits(): LiveData<List<Habit>>
//
    @Query("SELECT * FROM habits WHERE id = :uuid")
    fun getHabitById(uuid : Long) : LiveData<Habit>


    @Query(
        "SELECT * FROM habits " +
                "WHERE habit_type_res_id = :typeResId AND title LIKE '%'|| :searchQuery ||'%'" +
                "ORDER BY CASE " +
                "WHEN 'ascending' = :sortingMode THEN priority " +
                "WHEN 'descending' = :sortingMode THEN (priority * -1) " +
                "END ASC"
                )
    fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : LiveData<List<Habit>>

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun editHabit(habit: Habit)
}