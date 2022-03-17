package com.example.task3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.R
import com.example.task3.objects.TimeIntervalType
import kotlinx.android.synthetic.main.habit_form.*

class HabitCreatingForm : AppCompatActivity() {

    private val priorities = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    private val typesOfTimeInterval = TimeIntervalType.values().map { it.value }
    private var currentTypeString = "useful"
    private var resultCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_form)

        priority_input.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, priorities)
        )

       time_interval_input.setAdapter(
           ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, typesOfTimeInterval)
       )

        useful_type_radiobutton.setOnClickListener {
            currentTypeString = "useful"
            count_of_events_input.hint = getString(R.string.hint_events_count_useful)
        }

        neutral_type_radiobutton.setOnClickListener {
            currentTypeString = "neutral"
            count_of_events_input.hint = getString(R.string.hint_events_count_neutral)
        }

        bad_type_radiobutton.setOnClickListener {
            currentTypeString = "bad"
            count_of_events_input.hint = getString(R.string.hint_events_count_bad)
        }

        useful_type_radiobutton.isChecked = true
        count_of_events_input.hint = getString(R.string.hint_events_count_useful)

        resultCode = HabitsList.RESULT_CREATING_OK

        button.setOnClickListener {

            val title = title_input.text.toString()
            val description = description_input.text.toString()
            val priorityString = priority_input.text.toString()
            val countOfEventsString = count_of_events_input.text.toString()
            val timeIntervalTypeString = time_interval_input.text.toString()

            if (title.isEmpty() or priorityString.isEmpty() or countOfEventsString.isEmpty() or timeIntervalTypeString.isEmpty()) {
                Toast.makeText(this, "Please, fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val listIntent = Intent(this, HabitsList::class.java).apply {
                val bundle = Bundle().apply {
                    putExtra("title", title)
                    putExtra("description", description)
                    putExtra("priority", priorityString.toInt())
                    putExtra("type", currentTypeString)
                    putExtra("eventsCount", countOfEventsString.toInt())
                    putExtra("timeIntervalType", timeIntervalTypeString)

                    if (intent.hasExtra("id")) putExtra("id", intent.getStringExtra("id"))
                }
            }

            setResult(resultCode, listIntent)
            finish()
        }

        if (intent.hasExtra("id")) {
            title_input.setText(intent.getStringExtra("title"))
            description_input.setText(intent.getStringExtra("description"))
            priority_input.setText(intent.getIntExtra("priority", -1).toString())
            when (intent.getStringExtra("type")) {
                "useful" -> useful_type_radiobutton.isChecked = true
                "neutral" -> neutral_type_radiobutton.isChecked = true
                "bad" -> bad_type_radiobutton.isChecked = true
            }
            count_of_events_input.setText(intent.getIntExtra("eventsCount", 0).toString())
            time_interval_input.setText(intent.getStringExtra("timeIntervalType"))

            button.text = resources.getString(R.string.edit_button_text)

            resultCode = HabitsList.RESULT_EDITING_OK

        }
    }
}