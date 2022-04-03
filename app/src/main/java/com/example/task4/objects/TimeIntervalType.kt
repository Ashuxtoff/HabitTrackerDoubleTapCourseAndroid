package com.example.task3.objects

import androidx.room.ColumnInfo
import com.example.task4.R

enum class TimeIntervalType(
    @ColumnInfo(name = "time_interval_type_res_id")
    val resId : Int
) {

    HOURS(R.string.hourTimeIntervalKey),
    DAYS(R.string.dayTimeIntervalKey),
    WEEKS(R.string.weekTimeIntervalKey),
    MONTHS(R.string.monthTimeIntervalKey);

}