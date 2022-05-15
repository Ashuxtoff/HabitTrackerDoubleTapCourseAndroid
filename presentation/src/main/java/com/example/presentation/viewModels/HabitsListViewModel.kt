package com.example.task4.viewModels

import androidx.lifecycle.*
import com.example.data.repository.RepositoryImpl
import com.example.task3.objects.Habit
import com.example.task3.objects.HabitType
import kotlinx.coroutines.*

class HabitsListViewModel(private val repository : RepositoryImpl) : ViewModel() {

    companion object {
        private const val EMPTY_STRING = ""
    }


    private val mutableIsUsefulCurrent: MutableLiveData<Boolean> = MutableLiveData()
    private val mutableSortingMode: MutableLiveData<String> = MutableLiveData(EMPTY_STRING)
    private val mutableSearchQuery: MutableLiveData<String> = MutableLiveData(EMPTY_STRING)

    private val mutableCurrentHabitsList : MutableLiveData<List<Habit>> = MutableLiveData<List<Habit>>()

    val sortingMode : LiveData<String> = mutableSortingMode
    val searchQuery : LiveData<String> = mutableSearchQuery

    //private val mediatorLiveData = MediatorLiveData<List<Habit>>()

    val  currentHabitsList : LiveData<List<Habit>> = mutableCurrentHabitsList


    fun setCurrentHabitsList(isUsefulHabitsCurrent: Boolean) {
        mutableIsUsefulCurrent.value = isUsefulHabitsCurrent
        loadCurrentListHabits()
        //перевычислять currentHabitList?
        //а мы не можем, это же не мьютабл
    }

    fun setSortingMode(mode : String?) {
        mutableSortingMode.value = mode ?: EMPTY_STRING
        loadCurrentListHabits()
    }

    fun setSearchQuery(query : String?) {
        mutableSearchQuery.value = query ?: EMPTY_STRING
        loadCurrentListHabits()
    }

//    private fun transform(sortingMode : String) : LiveData<List<Habit>> {
//        return repository.getCurrentHabits(HabitType.USEFUL.resId, sortingMode, searchQuery.value ?: "")


    private fun loadCurrentListHabits() {

        viewModelScope.launch(Dispatchers.Main) {

                val habits =
                    withContext(Dispatchers.IO) {
                    repository.getCurrentHabits(
                        if (mutableIsUsefulCurrent.value == false) {
                            HabitType.BAD.resId
                        } else HabitType.USEFUL.resId,

                        sortingMode.value ?: EMPTY_STRING,

                        searchQuery.value ?: EMPTY_STRING
                    )
            }

            mutableCurrentHabitsList.value = habits
        }
    }

//    fun getCurrentHabitsList() : LiveData<List<Habit>> {
//        return currentHabitsList
//    }

}























//    private var mutableCurrentHabitsList: MutableLiveData<List<Habit>> = MutableLiveData()
//    private val mutableIsUsefulCurrent: MutableLiveData<Boolean> = MutableLiveData()
//    private val mutableSortingMode: MutableLiveData<String?> = MutableLiveData()
//    private val mutableSearchQuery: MutableLiveData<String> = MutableLiveData()
//
//    val currentHabitsList: LiveData<List<Habit>> = mutableCurrentHabitsList
//    val sortingMode : LiveData<String?> = mutableSortingMode
//    val searchQuery : LiveData<String> = mutableSearchQuery
//
//    init {
//
//        //подписаться тут на лайв дату из репозитория? Но нам нужна активити для держания ЖЦ, а VM ничего не должна знать о вью
//        //или делать запросы в бидэ с сортировкой и поиском?
//        //если получать из бидэ только данные о всех привычках
//        //то нам нужно их сортировать/фильтровать в VM. Для этого нужна MutableLD
//        //просто так мы получить MLD из LD не можем => нельзя просто дать ссылку на LD из БД мы не можем
//        //назначать обсервер - тоже плохо из-за того, что я выше прописал
//        //кажется единственный вариант - сортировка и поиск прямо из БД, а во VM просто ссылка на результат
//
//        // итого: ссылка на ЛД репы, и обсервить в фрагменте эту ЛД
//
//        //mutableCurrentHabitsList.value = repository.getAllHabits().value
//        mutableSearchQuery.value = EMPTY_STRING
//    }
//
//    fun setCurrentHabitsList(isUsefulHabitsCurrent: Boolean) {
//        mutableIsUsefulCurrent.value = isUsefulHabitsCurrent
//        calculateCurrentHabitsList()
//    }
//
//    fun setSortingMode(mode : String?) {
//        mutableSortingMode.value = mode
//        calculateCurrentHabitsList()
//    }
//
//    fun setSearchQuery(query : String?) {
//        mutableSearchQuery.value = query
//        calculateCurrentHabitsList()
//    }
//
//    private fun applyCurrentList() {
//        mutableCurrentHabitsList = if (mutableIsUsefulCurrent.value == true) {
//            repository.getUsefulHabits() as MutableLiveData<List<Habit>>
//        } else {
//            repository.getBadHabits() as MutableLiveData<List<Habit>>
//        }
//    }
//
//    private fun applySorting() {
//        mutableCurrentHabitsList.value = when (mutableSortingMode.value) {
//            ASCENDING_SORTING_MODE -> mutableCurrentHabitsList.value?.sortedBy { it.priority }
//            DESCENDING_SORTING_MODE -> mutableCurrentHabitsList.value?.sortedByDescending { it.priority }
//            else -> mutableCurrentHabitsList.value
//        }
//    }
//
//    private fun applySearch() {
//        mutableCurrentHabitsList.value = mutableCurrentHabitsList.value?.filter { it.title.contains(mutableSearchQuery.value.toString()) }
//    }
//
//
//    private fun calculateCurrentHabitsList() {
//        applyCurrentList()
//        applySorting()
//        applySearch()
//    }
//}