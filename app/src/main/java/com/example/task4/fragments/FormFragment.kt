package com.example.task4.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.MainActivity
import com.example.task4.R
import com.example.task4.repository.Repository
import com.example.task4.viewModels.FormViewModel
import kotlinx.android.synthetic.main.fragment_form.*


class FormFragment : Fragment(), TextWatcher {

    private val priorities get() = resources.getStringArray(R.array.priorities)
    private val typesOfTimeInterval get() = TimeIntervalType.values().map { getString(it.resId) }
    private var currentType = HabitType.USEFUL
    private var currentTimeIntervalType = TimeIntervalType.DAYS

    private var editedHabit : Habit? = null


    private lateinit var formViewModel : FormViewModel


    companion object {

        private const val ID_ARG = "id"


        fun newInstance(habitId : Long?) : FormFragment{
            val fragment = FormFragment()

            if (habitId != null) {
                val bundle = Bundle().apply {
                    putLong(ID_ARG, habitId)
                }
                fragment.arguments = bundle
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        formViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FormViewModel(Repository(requireContext()), arguments?.getLong(ID_ARG)) as T
            }
        }).get(FormViewModel::class.java)
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
            currentType = HabitType.USEFUL
            countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        }

        badTypeRadiobutton.setOnClickListener {
            currentType = HabitType.BAD
            countOfEventsInput.hint = getString(R.string.hintEventsCountBad)
        }

        usefulTypeRadiobutton.isChecked = true
        countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        currentType = HabitType.USEFUL

        formViewModel.getEditedHabitLiveData()?.observe(this.activity as LifecycleOwner) { habit ->
            editedHabit = habit

            if (editedHabit != null && this.isVisible) {
                titleInput.setText(editedHabit?.title)
                descriptionInput.setText(editedHabit?.description)
                priorityInput.setText(editedHabit?.priority.toString() as CharSequence)

                when (editedHabit?.type) {
                    HabitType.USEFUL -> {
                        usefulTypeRadiobutton.isChecked = true
                        currentType = HabitType.USEFUL
                    }
                    HabitType.BAD -> {
                        badTypeRadiobutton.isChecked = true
                        currentType = HabitType.BAD
                    }
                }

                countOfEventsInput.setText(editedHabit?.eventsCount.toString() as CharSequence)
                currentTimeIntervalType = editedHabit?.timeIntervalType ?: TimeIntervalType.DAYS
                timeIntervalInput.setText(getText(currentTimeIntervalType.resId))

                button.text = getString(R.string.editButtonText)
            }
        }



        button.setOnClickListener {

            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val priorityString = priorityInput.text.toString()
            val countOfEventsString = countOfEventsInput.text.toString()

            if (title.isEmpty() || priorityString.isEmpty() || countOfEventsString.isEmpty() || timeIntervalInput.text.toString().isEmpty()) {
                Toast.makeText(view.context, getString(R.string.emptyRequiredFieldsToast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            formViewModel.processForm(
                title,
                description,
                priorityString.toInt(),
                currentType,
                countOfEventsString.toInt(),
                currentTimeIntervalType
            )


            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
                .commit()
        }
    }


    //Text Watcher methods
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        currentTimeIntervalType = TimeIntervalType.values().find { getString(it.resId)  == text.toString()} ?: TimeIntervalType.DAYS
    }

    override fun afterTextChanged(p0: Editable?) {
        return
    }


}