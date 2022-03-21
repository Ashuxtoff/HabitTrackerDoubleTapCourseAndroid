package com.example.task4.fragments

import android.content.Context
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.FormOpeningCallback
import com.example.task4.FormResultCallback
import com.example.task4.R
import kotlinx.android.synthetic.main.fragment_form.*


class FormFragment : Fragment(), TextWatcher {

    private val priorities get() = resources.getStringArray(R.array.priorities)
    private val typesOfTimeInterval get() = TimeIntervalType.values().map { getString(it.resId) }
    private var currentTypeId = R.string.usefulHabitKey
    private var currentTimeIntervalTypeId = -1
    private var callback: FormResultCallback? = null


    companion object {

        private const val HABIT_ARG = "habit"
        private const val ID_ARG = "id"


        fun newInstance(habit : Habit?) : FormFragment{
            val fragment = FormFragment()

            if (habit != null) {
                val bundle = Bundle().apply {
                    putParcelable(HABIT_ARG, habit)
                    putString(ID_ARG, habit.uniqueId)
                }
                fragment.arguments = bundle
            }

            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as FormResultCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        priority_input.setAdapter(
            ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, priorities)
        )

        time_interval_input.apply {
            setAdapter(
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_dropdown_item_1line,
                    typesOfTimeInterval
                )
            )
            addTextChangedListener(this@FormFragment)
        }

        useful_type_radiobutton.setOnClickListener {
            currentTypeId = R.string.usefulHabitKey
            count_of_events_input.hint = getString(R.string.hintEventsCountUseful)
        }

        bad_type_radiobutton.setOnClickListener {
            currentTypeId = R.string.badHabitKey
            count_of_events_input.hint = getString(R.string.hintEventsCountBad)
        }

        useful_type_radiobutton.isChecked = true
        count_of_events_input.hint = getString(R.string.hintEventsCountUseful)
        currentTypeId = R.string.usefulHabitKey

        arguments?.let { bundle ->
            val habit = bundle.getParcelable(HABIT_ARG) as Habit?

            title_input.setText(habit?.title)
            description_input.setText(habit?.description)
            priority_input.setText(habit?.priority.toString() as CharSequence)

            when (habit?.type) {
                HabitType.USEFUL -> {
                    useful_type_radiobutton.isChecked = true
                    currentTypeId = R.string.usefulHabitKey
                }
                HabitType.BAD -> {
                    bad_type_radiobutton.isChecked = true
                    currentTypeId = R.string.badHabitKey
                }
            }

            count_of_events_input.setText(habit?.eventsCount.toString() as CharSequence)
            currentTimeIntervalTypeId = habit?.timeIntervalType?.resId ?: -1
            time_interval_input.setText(getText(currentTimeIntervalTypeId))

            button.text = getString(R.string.editButtonText)
        }

        button.setOnClickListener {

            var resultHabit : Habit? = arguments?.getParcelable(HABIT_ARG) as Habit?

            val title = title_input.text.toString()
            val description = description_input.text.toString()
            val priorityString = priority_input.text.toString()
            val countOfEventsString = count_of_events_input.text.toString()

            if (title.isEmpty() || priorityString.isEmpty() || countOfEventsString.isEmpty() || currentTimeIntervalTypeId == -1) {
                Toast.makeText(view.context, getString(R.string.emptyRequiredFieldsToast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (arguments == null) {
                resultHabit = Habit(
                    title,
                    description,
                    priorityString.toInt(),
                    HabitType.from(currentTypeId),
                    countOfEventsString.toInt(),
                    TimeIntervalType.from(currentTimeIntervalTypeId)
                )

            }
            else {
                resultHabit?.edit(
                    title,
                    description,
                    priorityString.toInt(),
                    HabitType.from(currentTypeId),
                    countOfEventsString.toInt(),
                    TimeIntervalType.from(currentTimeIntervalTypeId)
                )
            }

            callback?.processForm(resultHabit, arguments?.getString(ID_ARG))
        }
    }


    //Text Watcher methods
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