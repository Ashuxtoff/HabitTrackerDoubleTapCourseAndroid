package com.example.task4.typeConverters

import androidx.room.TypeConverter
import com.example.task3.objects.HabitType
import com.example.task3.objects.TimeIntervalType
import com.example.task4.R

class TimeIntervalTypeConverter {

    @TypeConverter
    fun fromTimeIntervalType(type : TimeIntervalType) : Int {
        return type.resId
    }

    @TypeConverter
    fun toTimeIntervalType(resId : Int) : TimeIntervalType {
        return when (resId) {
            R.string.hourTimeIntervalKey -> TimeIntervalType.HOURS
            R.string.dayTimeIntervalKey -> TimeIntervalType.DAYS
            R.string.weekTimeIntervalKey -> TimeIntervalType.WEEKS
            else -> TimeIntervalType.MONTHS
        }
    }
}