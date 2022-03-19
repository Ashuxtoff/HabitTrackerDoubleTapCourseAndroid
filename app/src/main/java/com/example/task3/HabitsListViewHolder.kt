package com.example.task3

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.objects.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_habit_list.*


class HabitsListViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(habit : Habit, clickListener: OnItemClickListener) {
        habit_title.text = habit.title //пока я не сделал apply plugin в build.gradle и experimental = true, у меня не получалось увидеть это поле. Почему так, и правильно ли я решил проблему?
        habit_description.text = habit.description
        habit_priority.text = habit.priority.toString()
        habit_type.text = containerView.context.getString(habit.type.resId) // все это передам в гет стринг
        //habit_periodicity.text = "${habit.eventsCount} per ${habit.timeIntervalType.value}"

        habit_periodicity.text = containerView.context.getString(R.string.periodicityValueString, habit.eventsCount,
            containerView.context.getString(habit.timeIntervalType.resId))
        // почему так не получилось?
        // можно брать контекст из вью



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