package com.example.task4.fragments

import android.content.Context
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
import com.example.task4.FormResultCallback
import com.example.task4.MainActivity
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        if (arguments == null) {
            activity.supportActionBar?.setTitle(R.string.addHabitToolbarTitle)
        }
        else {
            activity.supportActionBar?.setTitle(R.string.editHabitToolbarTitle)
        }

        priorityInput.setAdapter(
            ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, priorities)
        )

        timeIntervalInput.apply {
            setAdapter(
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_dropdown_item_1line,
                    typesOfTimeInterval
                )
            )
            addTextChangedListener(this@FormFragment)
        }

        usefulTypeRadiobutton.setOnClickListener {
            currentTypeId = R.string.usefulHabitKey
            countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        }

        badTypeRadiobutton.setOnClickListener {
            currentTypeId = R.string.badHabitKey
            countOfEventsInput.hint = getString(R.string.hintEventsCountBad)
        }

        usefulTypeRadiobutton.isChecked = true
        countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        currentTypeId = R.string.usefulHabitKey

        arguments?.let { bundle ->
            val habit = bundle.getParcelable(HABIT_ARG) as Habit?

            titleInput.setText(habit?.title)
            descriptionInput.setText(habit?.description)
            priorityInput.setText(habit?.priority.toString() as CharSequence)

            when (habit?.type) {
                HabitType.USEFUL -> {
                    usefulTypeRadiobutton.isChecked = true
                    currentTypeId = R.string.usefulHabitKey
                }
                HabitType.BAD -> {
                    badTypeRadiobutton.isChecked = true
                    currentTypeId = R.string.badHabitKey
                }
            }

            countOfEventsInput.setText(habit?.eventsCount.toString() as CharSequence)
            currentTimeIntervalTypeId = habit?.timeIntervalType?.resId ?: -1
            timeIntervalInput.setText(getText(currentTimeIntervalTypeId))

            button.text = getString(R.string.editButtonText)
        }

        button.setOnClickListener {

            var resultHabit : Habit? = arguments?.getParcelable(HABIT_ARG) as Habit?

            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val priorityString = priorityInput.text.toString()
            val countOfEventsString = countOfEventsInput.text.toString()

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

            callback?.processForm(this, resultHabit, arguments?.getString(ID_ARG))
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