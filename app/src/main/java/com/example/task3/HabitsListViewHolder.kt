package com.example.task3

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.objects.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_habit_list.*
import com.example.task3.R


class HabitsListViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(habit : Habit, clickListener: OnItemClickListener, context : Context) {
        habit_title.text = habit.title //пока я не сделал apply plugin в build.gradle и experimental = true, у меня не получалось увидеть это поле. Почему так, и правильно ли я решил проблему?
        habit_description.text = habit.description
        habit_priority.text = habit.priority.toString()
        habit_type.text = habit.type.value
        habit_periodicity.text = "${habit.eventsCount} per ${habit.timeIntervalType.value}"

        //habit_periodicity.text = context.getString(R.string.periodicity_value_string, habit.eventsCount, habit.timeIntervalType.value)
        // почему так не получилось?

        containerView.setOnClickListener {
            clickListener.onItemClicked(habit)
        }
    }



}





//class HabitsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    private val title : TextView = itemView.findViewById(R.id.TitleValue)
//    private val description : TextView = itemView.findViewById(R.id.DescriptionValue)
//    private val priority : TextView = itemView.findViewById(R.id.PriorityValue)
//    private val type : TextView = itemView.findViewById(R.id.TypeValue)
//    private val periodicity : TextView = itemView.findViewById(R.id.PeriodicityValue)
//
//
//    fun bind(habit : Habit) {
//        title.text = habit.title
//        description.text = habit.description
//        priority.text = habit.priority.toString()
//        type.text = habit.type.value
//        periodicity.text = "${habit.eventsCount} per ${habit.timeIntervalsCount} ${habit.timeIntervalType.value}"
//