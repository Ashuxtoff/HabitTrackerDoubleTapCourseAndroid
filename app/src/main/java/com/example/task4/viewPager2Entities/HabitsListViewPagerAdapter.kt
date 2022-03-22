package com.example.task4.viewPager2Entities

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task4.fragments.HabitsListFragment

private const val HABITS_LIST_ARGS = "habitsList"

class HabitsListViewPagerAdapter(fragment : FragmentActivity, private val habits : MutableList<Habit>) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = HabitsListFragment()

        when (position) {
            0 -> {
                fragment.arguments = Bundle().apply {
                    putParcelableArrayList(
                        HABITS_LIST_ARGS,
                        habits.filter { it.type == HabitType.USEFUL } as ArrayList<out Habit>)
                }

            }

            1 -> {
                fragment.arguments = Bundle().apply {
                    putParcelableArrayList(
                        HABITS_LIST_ARGS,
                        habits.filter { it.type == HabitType.BAD } as ArrayList<out Habit>)
                }
            }
        }
        return fragment
    }
}