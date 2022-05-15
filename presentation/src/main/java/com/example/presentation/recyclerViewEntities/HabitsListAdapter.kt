package com.example.task3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.task3.objects.Habit

class HabitsListAdapter(private val habits: List<Habit>,
                        private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<HabitsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsListViewHolder(inflater.inflate(R.layout.item_habit_list, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsListViewHolder, position: Int) {
        holder.bind(habits[position], itemClickListener)
    }

    override fun getItemCount(): Int = habits.size

}