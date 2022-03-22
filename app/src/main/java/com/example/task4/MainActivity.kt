package com.example.task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.fragments.BaseHabitListFragment
import com.example.task4.fragments.FormFragment
import com.example.task4.fragments.HabitsListFragment
import com.example.task4.viewPager2Entities.HabitsListViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    FormOpeningCallback,
    FormResultCallback {


    companion object {
        private const val ADDING_MODE_KEY = "adding"
        private const val EDITING_MODE_KEY = "editing"
    }

    private var habits : MutableList<Habit> = mutableListOf()
//    private lateinit var viewPagerViewPagerAdapter : HabitsListViewPagerAdapter
//    private lateinit var habitsListViewPager : ViewPager2
//    private lateinit var listsTabLayout: TabLayout
//    private lateinit var tabsMediator: TabLayoutMediator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewPagerViewPagerAdapter = HabitsListViewPagerAdapter(this, habits)
//        habitsListViewPager = viewPager
//        habitsListViewPager.adapter = viewPagerViewPagerAdapter
//
//        listsTabLayout = tabLayout
//        tabsMediator = TabLayoutMediator(listsTabLayout, habitsListViewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> getString(R.string.usefulHabitKey)
//                1 -> getString(R.string.badHabitKey)
//                else -> getString(R.string.errorText)
//            }
//        }
//
//        tabsMediator.attach()


        habits.add(
            Habit("1st", "1st", 1, HabitType.BAD, 1, TimeIntervalType.DAYS)
        )

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, BaseHabitListFragment.newInstance(habits))
                //.replace(R.id.fragmentPlaceholder, HabitsListFragment.newInstance(habits.filter { it.type == HabitType.USEFUL }))
            .commit()
    }

    override fun openForm(habitForEditing: Habit?) {

//        habitsListViewPager.isUserInputEnabled = false
//        habitsListViewPager.adapter = null


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentPlaceholder, FormFragment.newInstance(habitForEditing))
            .commit()
    }

    override fun processForm(fragment : FormFragment, resultHabit : Habit?, habitId: String?) {
        val title = resultHabit?.title ?: "untitled"
        val description = resultHabit?.description ?: ""
        val priority = resultHabit?.priority ?: -1
        val type = resultHabit?.type ?: HabitType.USEFUL
        val eventsCount = resultHabit?.eventsCount ?: -1
        val timeIntervalType = resultHabit?.timeIntervalType ?: TimeIntervalType.HOURS

        if (habitId == null) {
            habits.add(Habit(
                title, description, priority, type, eventsCount, timeIntervalType
            ))
        }
        else {
            val habitForEdit = habits.find { it.uniqueId == habitId }
            habitForEdit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, BaseHabitListFragment.newInstance(habits))
            .commit()

//        habitsListViewPager.isUserInputEnabled = true
//        habitsListViewPager.adapter = viewPagerViewPagerAdapter


//        supportFragmentManager.popBackStack() почему-то не работает :(
    }
}
