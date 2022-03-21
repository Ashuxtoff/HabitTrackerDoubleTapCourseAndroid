package com.example.task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.fragments.FormFragment
import com.example.task4.fragments.HabitsListFragment

class MainActivity :
    AppCompatActivity(),
    FormOpeningCallback,
    FormResultCallback {


    companion object {
        private const val ADDING_MODE_KEY = "adding"
        private const val EDITING_MODE_KEY = "editing"
    }

    private var habits : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        habits.add(
            Habit("1st", "1st", 1, HabitType.USEFUL, 1, TimeIntervalType.DAYS)
        )

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, HabitsListFragment.newInstance(habits))
                //.replace(R.id.fragmentPlaceholder, HabitsListFragment.newInstance(habits.filter { it.type == HabitType.USEFUL }))
            .commit()
    }

    override fun openForm(habitForEditing: Habit?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentPlaceholder, FormFragment.newInstance(habitForEditing))
            .commit()
    }

    override fun processForm(resultHabit : Habit?, habitId: String?) {
        val title = resultHabit?.title ?: "untitled"
        val description = resultHabit?.description ?: ""
        val priority = resultHabit?.priority ?: -1
        val type = resultHabit?.type ?: HabitType.USEFUL
        val eventsCount = resultHabit?.eventsCount ?: -1
        val timeIntervalType = resultHabit?.timeIntervalType ?: TimeIntervalType.HOURS

        if (habitId == null) {
            habits.add(Habit(
                title, description, priority, type, eventsCount, timeIntervalType
            ))
        }
        else {
            val habitForEdit = habits.find { it.uniqueId == habitId }
            habitForEdit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentPlaceholder, HabitsListFragment.newInstance(habits))
            .commit()

//        supportFragmentManager.popBackStack() почему-то не работает :(
    }
}
