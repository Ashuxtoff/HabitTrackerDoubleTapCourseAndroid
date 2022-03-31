package com.example.task3.objects

import com.example.task4.R

enum class TimeIntervalType(val resId : Int) {
    HOURS(R.string.hourTimeIntervalKey),
    DAYS(R.string.dayTimeIntervalKey),
    WEEKS(R.string.weekTimeIntervalKey),
    MONTHS(R.string.monthTimeIntervalKey);

}