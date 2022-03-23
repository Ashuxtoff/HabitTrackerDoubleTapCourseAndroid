package com.example.task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.fragments.AppInformationFragment
import com.example.task4.fragments.BaseHabitsListFragment
import com.example.task4.fragments.FormFragment
import com.example.task4.fragments.HabitsListFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    FormOpeningCallback,
    FormResultCallback,
    NavigationView.OnNavigationItemSelectedListener {


    companion object {
        private const val ADDING_MODE_KEY = "adding"
        private const val EDITING_MODE_KEY = "editing"
    }

    private var habits : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerToggle = ActionBarDrawerToggle(this, navigationDrawerLayout, R.string.openDrawer, R.string.closeDrawer)
        navigationDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState() // cинхронизирует стотояние бургера или стрелка назад при открывании/закрывании меню
        navigationViewMenu.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.habitsListToolbarTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        habits.add(
            Habit("1st", "1st", 1, HabitType.BAD, 1, TimeIntervalType.DAYS)
        )

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance(habits))
            .commit()
    }

    override fun openForm(habitForEditing: Habit?) {

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
            .add(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance(habits))
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemStartScreen -> {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance(habits))
                    .commit()
            }

            R.id.menuItemAppInformation -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, AppInformationFragment.newInstance())
                    .commit()
            }
        }
        navigationDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            if (navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
                return true
            }

            val currentFragment =
                supportFragmentManager.fragments.last().childFragmentManager.fragments.find { it.isVisible }
                ?:
                supportFragmentManager.fragments.last()


            if (currentFragment != null &&
                (currentFragment is AppInformationFragment || currentFragment is FormFragment)
            ) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance(habits))
                    .commit()
            }

            if (currentFragment != null && currentFragment is HabitsListFragment) {
                navigationDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return true
    }
}
