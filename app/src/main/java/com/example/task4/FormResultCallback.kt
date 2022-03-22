package com.example.task4

import com.example.task3.objects.Habit
import com.example.task4.fragments.FormFragment

interface FormResultCallback {
    fun processForm(fragment : FormFragment, resultHabit : Habit?, habitId : String?)
}