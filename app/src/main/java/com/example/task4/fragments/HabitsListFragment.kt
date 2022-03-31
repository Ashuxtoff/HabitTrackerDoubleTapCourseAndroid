package com.example.task4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task3.HabitsListAdapter
import com.example.task3.OnItemClickListener
import com.example.task3.objects.Habit
import com.example.task4.R
import com.example.task4.model.Model
import com.example.task4.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.*
import kotlinx.android.synthetic.main.fragment_base_habit_list.*
import kotlin.random.Random


class HabitsListFragment : Fragment(), OnItemClickListener {

    var habits: MutableList<Habit> = mutableListOf()

    private lateinit var viewModel: HabitsListViewModel

    val pageId = Random.nextLong()

    companion object {
        private const val HABITS_LIST_ARGS = "isUseful"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this.requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //return HabitsListViewModel(Model, arguments?.getBoolean(HABITS_LIST_ARGS) ?: true) as T
                return HabitsListViewModel(Model) as T
            }
        }).get(HabitsListViewModel::class.java)
        viewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .add(R.id.bottomSheetFragmentPlaceholder, BottomSheetFragment())
            .commitNow()


        viewModel.currentHabitsList.observe(this.activity as LifecycleOwner, Observer {
//            habits = viewModel.currentHabitsList.value as MutableList<Habit>?: mutableListOf()
            habits.clear()
            habits.addAll(it)
            habitsRecyclerView.adapter?.notifyDataSetChanged()


        })

        habitsRecyclerView.adapter = HabitsListAdapter(habits, this)

        addHabit.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(null))
                ?.commit()
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
    }


    override fun onItemClicked(habit: Habit) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(habit))
            ?.commit()
    }



}