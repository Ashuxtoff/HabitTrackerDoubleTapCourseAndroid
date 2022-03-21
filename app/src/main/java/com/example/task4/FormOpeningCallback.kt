package com.example.task4

import com.example.task3.objects.Habit

interface FormOpeningCallback {
    fun openForm(habitForEditing : Habit?)
}