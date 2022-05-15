package com.example.task4.typeConverters

import androidx.room.TypeConverter
import com.example.data.R
import com.example.task3.objects.HabitType

class HabitTypeConverter {

    @TypeConverter
    fun fromHabitType(type : HabitType) : Int {
        return type.resId
    }

    @TypeConverter
    fun toHabitType(resId : Int) : HabitType {
        if (resId == R.string.usefulHabitKey) {
            return HabitType.USEFUL
        }
        return HabitType.BAD
    }
}