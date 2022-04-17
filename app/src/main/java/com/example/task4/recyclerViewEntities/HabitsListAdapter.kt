package com.example.task3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.objects.Habit
import com.example.task4.R

class HabitsListAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<HabitsListViewHolder>() {

     var habitsList: List<Habit> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsListViewHolder(inflater.inflate(R.layout.item_habit_list, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsListViewHolder, position: Int) {
        holder.bind(habitsList[position], itemClickListener)
    }

    override fun getItemCount(): Int = habitsList.size

}