package com.example.task3.objects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "habits")
class Habit(
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var priority: Int,
    @Embedded var type : HabitType,
    @ColumnInfo(name = "events_count") var eventsCount: Int,
    @Embedded var timeIntervalType : TimeIntervalType
) : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    val uniqueId = UUID.randomUUID().toString() // потом можно будет просто переделать на autoGenerate = true

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