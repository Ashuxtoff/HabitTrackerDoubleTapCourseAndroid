package com.example.presentation

import com.example.task3.objects.Habit

interface FormOpeningCallback {
    fun openForm(habitForEditing : Habit?)
}