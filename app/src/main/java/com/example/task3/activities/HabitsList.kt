package com.example.task3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.HabitsListAdapter
import com.example.task3.OnItemClickListener
import com.example.task3.R
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import kotlinx.android.synthetic.main.habit_list.*



class HabitsList : AppCompatActivity(), OnItemClickListener {

    private var habits : MutableList<Habit> = mutableListOf()

    companion object {
        const val RESULT_CREATING_OK = 1
        const val RESULT_EDITING_OK = 2

        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val PRIORITY = "priority"
        const val TYPE = "type"
        const val EVENTS_COUNT = "eventsCount"
        const val TIME_INTERVAL_TYPE = "timeIntervalType"
    }

    //val binding = HabitListBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_list)
        recycler_habits.adapter = HabitsListAdapter(habits, this)
        add_habit.setOnClickListener {
            val intent = Intent(this, HabitCreatingForm::class.java)
            getResult.launch(intent)
        }

        habits.add(
            Habit("1st", "1st", 1, HabitType.USEFUL, 1, TimeIntervalType.DAYS)
        )
        habits.add(
            Habit("2nd", "2nd", 2, HabitType.NEUTRAL, 2, TimeIntervalType.WEEKS)
        )
        habits.add(
            Habit("3rd", "3rd", 3, HabitType.BAD, 3, TimeIntervalType.MONTHS)
        )
    }

    override fun onItemClicked(habit: Habit) {

        val intent = Intent(this, HabitCreatingForm::class.java).apply {
            val bundle = Bundle().apply {
                putString(ID, habit.uniqueId) // решил, что лучше так, потому что в условиях многопоточнсти и будущей докрутки функционала с индексом могут быть проблемы
                putString(TITLE, habit.title)
                putString(DESCRIPTION, habit.description)
                putInt(PRIORITY, habit.priority)
                putInt(TYPE, habit.type.resId)
                putInt(EVENTS_COUNT, habit.eventsCount)
                putInt(TIME_INTERVAL_TYPE, habit.timeIntervalType.resId)
            }
            putExtras(bundle)
        }
        getResult.launch(intent)
    }


    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == RESULT_CREATING_OK || result.resultCode == RESULT_EDITING_OK) {
            result.data?.apply {
                val id = getStringExtra(ID)
                val title = getStringExtra(TITLE) ?: "untitled"
                val description = getStringExtra(DESCRIPTION) ?: ""
                val priority = getIntExtra(PRIORITY, -1)
                val type = getIntExtra(TYPE, R.string.usefulHabitKey)
                val eventsCount = getIntExtra(EVENTS_COUNT, -1)
                val timeIntervalType = getIntExtra(TIME_INTERVAL_TYPE, R.string.hourTimeIntervalKey)


                when (result.resultCode) {
                    RESULT_CREATING_OK -> {
                        habits.add(
                            Habit(
                                title,
                                description,
                                priority,
                                HabitType.from(type),
                                eventsCount,
                                TimeIntervalType.from(timeIntervalType)
                            )
                        )
                        recycler_habits.adapter?.notifyItemInserted(habits.size - 1)
                    }

                    RESULT_EDITING_OK -> {
                        val habit = habits.find { it.uniqueId == id }
                        habit?.edit(
                            title,
                            description,
                            priority,
                            HabitType.from(type),
                            eventsCount,
                            TimeIntervalType.from(timeIntervalType)
                        )
                        recycler_habits.adapter?.notifyItemChanged(habits.indexOf(habit))
                    }
                }
            }

        }
    }

}