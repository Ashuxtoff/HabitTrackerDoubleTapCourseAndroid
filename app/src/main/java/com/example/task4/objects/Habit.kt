package com.example.task3.objects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.example.task4.typeConverters.HabitTypeConverter
import com.example.task4.typeConverters.TimeIntervalTypeConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "habits")
class Habit(
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var priority: Int,
    @field:TypeConverters(HabitTypeConverter::class)
    @ColumnInfo(name = "habit_type_res_id")
    var type : HabitType,
    @ColumnInfo(name = "events_count") var eventsCount: Int,
    @field:TypeConverters(TimeIntervalTypeConverter::class)
    @ColumnInfo(name = "time_interval_type_res_id")
    var timeIntervalType : TimeIntervalType
) : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var uniqueId : String = ""

    //val colorString = colorString


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
             newType : HabitType, newEventsCount : Int, newTimeIntervalType : TimeIntervalType) {

        title = newTitle
        description = newDescription
        priority = newPriority
        type = newType
        eventsCount = newEventsCount
        timeIntervalType = newTimeIntervalType
    }


}