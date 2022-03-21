package com.example.task4

import com.example.task3.objects.Habit

interface FormResultCallback {
    fun processForm(resultHabit : Habit?, habitId : String?)
}