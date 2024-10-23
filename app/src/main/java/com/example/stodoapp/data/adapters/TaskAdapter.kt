package com.example.stodoapp.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stodoapp.R
import com.example.stodoapp.data.models.Task


class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskChecked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onTaskClick, onTaskChecked)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTaskName)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxTask)

        fun bind(task: Task, onTaskClick: (Task) -> Unit, onTaskChecked: (Task) -> Unit) {
            titleTextView.text = task.title
            checkBox.isChecked = task.isCompleted

            itemView.setOnClickListener { onTaskClick(task) }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
                onTaskChecked(task)
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}