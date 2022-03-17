package com.example.task3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task3.HabitsListAdapter
import com.example.task3.OnItemClickListener
import com.example.task3.R
import com.example.task3.objects.Habit
import kotlinx.android.synthetic.main.habit_list.*



class HabitsList : AppCompatActivity(), OnItemClickListener {

    private var habits : MutableList<Habit> = mutableListOf()

    companion object {
        const val RESULT_CREATING_OK = 1
        const val RESULT_EDITING_OK = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_list)
        recycler_habits.adapter = HabitsListAdapter(habits, this, applicationContext)
        add_habit.setOnClickListener {
            val intent = Intent(this, HabitCreatingForm::class.java)
            getResult.launch(intent)
        }

        habits.add(
            Habit("1st", "1st", 1, "useful", 1, "hour")
        )
        habits.add(
            Habit("2nd", "2nd", 2, "neutral", 2, "day")
        )
        habits.add(
            Habit("3rd", "3rd", 3, "bad", 3, "week")
        )
    }

    override fun onItemClicked(habit: Habit) {

        val intent = Intent(this, HabitCreatingForm::class.java).apply {
            val bundle = Bundle().apply {
                putString("id", habit.uniqueId) // решил, что лучше так, потому что в условиях многопоточнсти и будущей докрутки функционала с индексом могут быть проблемы
                putString("title", habit.title)
                putString("description", habit.description)
                putInt("priority", habit.priority)
                putString("type", habit.type.value)
                putInt("eventsCount", habit.eventsCount)
                putString("timeIntervalType", habit.timeIntervalType.value)
            }
            putExtras(bundle)
        }
        getResult.launch(intent)
    }


    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == RESULT_CREATING_OK || result.resultCode == RESULT_EDITING_OK) {
            val data = result.data
            val id = data?.getStringExtra("id")
            val title = data?.getStringExtra("title") ?: "untitled"
            val description = data?.getStringExtra("description") ?: ""
            val priority = data?.getIntExtra("priority", -1) ?: 1
            val typeString = data?.getStringExtra("type") ?: "neutral"
            val eventsCount = data?.getIntExtra("eventsCount", -1) ?: 1
            val timeIntervalType = data?.getStringExtra("timeIntervalType") ?: "days"

            when (result.resultCode) {
                RESULT_CREATING_OK -> {
                    habits.add(Habit(title, description, priority, typeString, eventsCount, timeIntervalType))
                    recycler_habits.adapter!!.notifyItemInserted(habits.size - 1)
                }

                RESULT_EDITING_OK -> {
                    val habit = habits.find { it.uniqueId == id }
                    habit!!.edit(title, description, priority, typeString, eventsCount, timeIntervalType)
                    recycler_habits.adapter!!.notifyItemChanged(habits.indexOf(habit))
                }
            }

        }
    }

}