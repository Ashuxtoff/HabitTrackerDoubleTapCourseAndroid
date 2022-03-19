package com.example.task3.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.R
import com.example.task3.objects.TimeIntervalType
import kotlinx.android.synthetic.main.habit_form.*

class HabitCreatingForm : AppCompatActivity(), TextWatcher {

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val PRIORITY = "priority"
        const val TYPE = "type"
        const val EVENTS_COUNT = "eventsCount"
        const val TIME_INTERVAL_TYPE = "timeIntervalType"
    }

    private val priorities get() = resources.getStringArray(R.array.priorities)
    private val typesOfTimeInterval get() = TimeIntervalType.values().map { getString(it.resId) }
    private var currentTypeId = 0
    private var currentTimeIntervalTypeId = -1
    private var resultCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_form)

        priority_input.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, priorities)
        )


       time_interval_input.apply {
           setAdapter(ArrayAdapter(this.context, android.R.layout.simple_dropdown_item_1line, typesOfTimeInterval))
           addTextChangedListener(this@HabitCreatingForm)

       }

        useful_type_radiobutton.setOnClickListener {
            currentTypeId = R.string.usefulHabitKey
            count_of_events_input.hint = getString(R.string.hintEventsCountUseful)
        }

        neutral_type_radiobutton.setOnClickListener {
            currentTypeId = R.string.neutralHabitKey
            count_of_events_input.hint = getString(R.string.hintEventsCountNeutral)
        }

        bad_type_radiobutton.setOnClickListener {
            currentTypeId = R.string.badHabitKey
            count_of_events_input.hint = getString(R.string.hintEventsCountBad)
        }

        useful_type_radiobutton.isChecked = true
        count_of_events_input.hint = getString(R.string.hintEventsCountUseful)

        resultCode = HabitsList.RESULT_CREATING_OK

        button.setOnClickListener {

            val title = title_input.text.toString()
            val description = description_input.text.toString()
            val priorityString = priority_input.text.toString()
            val countOfEventsString = count_of_events_input.text.toString()

            if (title.isEmpty() || priorityString.isEmpty() || countOfEventsString.isEmpty() || currentTimeIntervalTypeId == -1) {
                Toast.makeText(this, getString(R.string.emptyRequiredFieldsToast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val listIntent = Intent(this, HabitsList::class.java).apply {
                val bundle = Bundle().apply {
                    putExtra(TITLE, title) //todo вынести константы
                    putExtra(DESCRIPTION, description)
                    putExtra(PRIORITY, priorityString.toInt())
                    putExtra(TYPE, currentTypeId)
                    putExtra(EVENTS_COUNT, countOfEventsString.toInt())
                    putExtra(TIME_INTERVAL_TYPE, currentTimeIntervalTypeId)

                    if (intent.hasExtra(ID)) putExtra(ID, intent.getStringExtra(ID))
                }
                putExtras(bundle)
            }

            setResult(resultCode, listIntent)
            finish()
        }

        if (intent.hasExtra(ID)) {
            title_input.setText(intent.getStringExtra(TITLE))
            description_input.setText(intent.getStringExtra(DESCRIPTION))
            priority_input.setText(intent.getIntExtra(PRIORITY, -1).toString())
            when (intent.getIntExtra(TYPE, -1)) {
                R.string.usefulHabitKey -> useful_type_radiobutton.isChecked = true
                R.string.neutralHabitKey -> neutral_type_radiobutton.isChecked = true
                R.string.badHabitKey -> bad_type_radiobutton.isChecked = true
            }
            count_of_events_input.setText(intent.getIntExtra(EVENTS_COUNT, 0).toString())
            currentTimeIntervalTypeId = intent.getIntExtra(TIME_INTERVAL_TYPE, -1)
            time_interval_input.setText(getString(currentTimeIntervalTypeId))

            button.text = resources.getString(R.string.editButtonText)

            resultCode = HabitsList.RESULT_EDITING_OK

        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        currentTimeIntervalTypeId = TimeIntervalType.values().find { getString(it.resId)  == text.toString()}?.resId ?: -1
    }

    override fun afterTextChanged(p0: Editable?) {
        return
    }

}