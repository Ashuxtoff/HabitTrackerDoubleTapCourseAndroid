package com.example.task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerToggle = ActionBarDrawerToggle(this, navigationDrawerLayout, R.string.openDrawer, R.string.closeDrawer)
        navigationDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState() // cинхронизирует стотояние бургера или стрелка назад при открывании/закрывании меню??
        navigationViewMenu.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.habitsListToolbarTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
            .commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemStartScreen -> {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
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
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
                    .commit()
            }

            if (currentFragment != null &&
                (currentFragment is BottomSheetFragment || currentFragment is HabitsListFragment)) {
                navigationDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return true
    }
}
