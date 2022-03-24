package com.example.task4.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.task3.objects.Habit
import com.example.task4.MainActivity
import com.example.task4.R
import com.example.task4.viewPager2Entities.HabitsListViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_base_habit_list.*
import java.util.ArrayList


class BaseHabitsListFragment : Fragment() {
    private lateinit var viewPagerViewPagerAdapter : HabitsListViewPagerAdapter
    private lateinit var habitsListViewPager : ViewPager2
    private lateinit var listsTabLayout: TabLayout
    private lateinit var tabsMediator: TabLayoutMediator

    private var habits : MutableList<Habit> = mutableListOf()

    companion object {
        private const val HABITS_LIST_ARGS = "habitsList"

        fun newInstance(habitsForViewing : List<Habit>) : BaseHabitsListFragment {
            val fragment = BaseHabitsListFragment()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger_menu)

        habits = arguments?.getParcelableArrayList<Habit>(HABITS_LIST_ARGS) as MutableList<Habit>

        viewPagerViewPagerAdapter = HabitsListViewPagerAdapter(this.requireActivity(), habits)
        habitsListViewPager = viewPager
        habitsListViewPager.adapter = viewPagerViewPagerAdapter

        listsTabLayout = tabLayout
        TabLayoutMediator(listsTabLayout, habitsListViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.usefulHabitKey)
                1 -> getString(R.string.badHabitKey)
                else -> getString(R.string.errorText)
            }
        }.attach()

    }

}