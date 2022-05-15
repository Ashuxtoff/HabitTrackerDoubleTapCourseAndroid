package com.example.presentation

import com.example.task3.objects.Habit
import com.example.task4.fragments.FormFragment

interface FormResultCallback {
    fun processForm(fragment : FormFragment, resultHabit : Habit?, habitId : String?)
}