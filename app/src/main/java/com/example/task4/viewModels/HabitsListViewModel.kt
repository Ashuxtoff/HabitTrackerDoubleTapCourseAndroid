package com.example.task4.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task3.objects.Habit
import com.example.task4.model.Model

class HabitsListViewModel(private val model : Model) : ViewModel() {

    companion object {
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
        private const val EMPTY_STRING = ""
    }

    private val mutableCurrentHabitsList: MutableLiveData<List<Habit>> = MutableLiveData()
    private val mutableIsUsefulCurrent: MutableLiveData<Boolean> = MutableLiveData()
    private val mutableSortingMode: MutableLiveData<String?> = MutableLiveData()
    private val mutableSearchQuery: MutableLiveData<String> = MutableLiveData()

    val currentHabitsList: LiveData<List<Habit>> = mutableCurrentHabitsList
    val sortingMode : LiveData<String?> = mutableSortingMode
    val searchQuery : LiveData<String> = mutableSearchQuery

    private val previousSearchQuery : String = EMPTY_STRING

    init {
        mutableCurrentHabitsList.value = model.getAllHabits()
        mutableSearchQuery.value = EMPTY_STRING
    }

    fun setCurrentHabitsList(isUsefulHabitsCurrent: Boolean) {
        mutableIsUsefulCurrent.value = isUsefulHabitsCurrent
        calculateCurrentHabitsList()
    }

    fun setSortingMode(mode : String?) {
        mutableSortingMode.value = mode
        calculateCurrentHabitsList()
    }

    fun setSearchQuery(query : String?) {
        mutableSearchQuery.value = query
        calculateCurrentHabitsList()
    }

    private fun applyCurrentList() {
        if (mutableIsUsefulCurrent.value == true) {
            mutableCurrentHabitsList.value = model.getUsefulHabits()
        }
        else {
            mutableCurrentHabitsList.value = model.getBadHabits()
        }
    }

    private fun applySorting() {
        mutableCurrentHabitsList.value = when (mutableSortingMode.value) {
            ASCENDING_SORTING_MODE -> mutableCurrentHabitsList.value?.sortedBy { it.priority }
            DESCENDING_SORTING_MODE -> mutableCurrentHabitsList.value?.sortedByDescending { it.priority }
            else -> mutableCurrentHabitsList.value
        }
    }

    private fun applySearch() {
        mutableCurrentHabitsList.value = mutableCurrentHabitsList.value?.filter { it.title.contains(mutableSearchQuery.value.toString()) }
    }


    private fun calculateCurrentHabitsList() {
        applyCurrentList()
        applySorting()
        applySearch()
    }
}