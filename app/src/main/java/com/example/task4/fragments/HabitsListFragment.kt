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
import com.example.task4.repository.Repository
import com.example.task4.viewModels.FormViewModel
import com.example.task4.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.*
import kotlin.random.Random


class HabitsListFragment : Fragment(), OnItemClickListener {

//    var habits: MutableList<Habit> = mutableListOf()

    private lateinit var habitsListViewModel: HabitsListViewModel
    private lateinit var formViewModel: FormViewModel

    val pageId = Random.nextLong()

    companion object {
        private const val HABITS_LIST_ARGS = "isUseful"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        habitsListViewModel = ViewModelProvider(this.requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(Repository(requireContext())) as T
            }
        }).get(HabitsListViewModel::class.java)
        habitsListViewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitsRecyclerView.adapter = HabitsListAdapter(this)

        childFragmentManager
            .beginTransaction()
            .add(R.id.bottomSheetFragmentPlaceholder, BottomSheetFragment())
            .commitNow()


        habitsListViewModel.getCurrentHabitsList().observe(this.activity as LifecycleOwner, Observer {
//          habits.clear()
//          habits.addAll(it)
            val adapter = habitsRecyclerView.adapter as HabitsListAdapter
            adapter.habitsList = it
        })

        addHabit.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(null))
                ?.commit()
        }

    }


    override fun onResume() {
        super.onResume()
        habitsListViewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
    }


    override fun onItemClicked(habit: Habit) {
        formViewModel = ViewModelProvider(this.requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FormViewModel(Repository(requireContext()), habit.uniqueId) as T
            }
        }).get(FormViewModel::class.java)


        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(habit.uniqueId))
            ?.commit()
    }

}