package com.example.task3.objects

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Habit(
    var title: String, var description: String, var priority: Int, var type : HabitType, // можно сразу предавать тип
    var eventsCount: Int, var timeIntervalType : TimeIntervalType) : Parcelable {

    val uniqueId = UUID.randomUUID().toString()

    //val colorString = colorString

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        HabitType.from(parcel.readInt()),
        parcel.readInt(),
        TimeIntervalType.from(parcel.readInt())
    )


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
             newType : HabitType, newEventsCount : Int, newTimeIntervalType : TimeIntervalType) {

        title = newTitle
        description = newDescription
        priority = newPriority
        type = newType
        eventsCount = newEventsCount
        timeIntervalType = newTimeIntervalType
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(priority)
        parcel.writeInt(type.resId)
        parcel.writeInt(eventsCount)
        parcel.writeInt(timeIntervalType.resId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit {
            return Habit(parcel)
        }

        override fun newArray(size: Int): Array<Habit?> {
            return arrayOfNulls(size)
        }
    }


}