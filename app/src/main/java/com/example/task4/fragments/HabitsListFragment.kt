package com.example.task4.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task3.HabitsListAdapter
import com.example.task3.OnItemClickListener
import com.example.task3.objects.Habit
import com.example.task4.FormOpeningCallback
import com.example.task4.R
import kotlinx.android.synthetic.main.fragment_habits_list.*
import java.util.ArrayList


class HabitsListFragment : Fragment(), OnItemClickListener {

    private var habits : MutableList<Habit> = mutableListOf()
    private var callback: FormOpeningCallback? = null

    companion object {
        private const val HABITS_LIST_ARGS = "habitsList"

        fun newInstance(habitsForViewing : List<Habit>) : HabitsListFragment {
            val fragment = HabitsListFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    HABITS_LIST_ARGS,
                    habitsForViewing as ArrayList<out Parcelable>
                )
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as FormOpeningCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habits = arguments?.getParcelableArrayList<Habit>(HABITS_LIST_ARGS) as MutableList<Habit>

        habitsRecyclerView.adapter = HabitsListAdapter(habits, this)

        addHabit.setOnClickListener {
            callback?.openForm(null)
        }
    }


    override fun onItemClicked(habit: Habit) {
        callback?.openForm(habit)
    }



}