package com.example.stodoapp.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stodoapp.data.models.Task

class TaskViewModel : ViewModel() {

    private val _pendingTasks = MutableLiveData<List<Task>>(emptyList())
    val pendingTasks: LiveData<List<Task>> get() = _pendingTasks

    private val _completedTasks = MutableLiveData<List<Task>>(emptyList())
    val completedTasks: LiveData<List<Task>> get() = _completedTasks

    fun addTask(task: Task) {
        if (task.isCompleted) {
            val completedTasksList = _completedTasks.value.orEmpty().toMutableList()
            completedTasksList.add(task)
            _completedTasks.value = completedTasksList
        } else {
            val pendingTasksList = _pendingTasks.value.orEmpty().toMutableList()
            pendingTasksList.add(task)
            _pendingTasks.value = pendingTasksList
        }
    }


    fun updateTaskCompletion(task: Task) {
        val isCurrentlyCompleted = task.isCompleted

        val pendingTasksList = _pendingTasks.value.orEmpty().toMutableList()
        val completedTasksList = _completedTasks.value.orEmpty().toMutableList()

        if (isCurrentlyCompleted) {
            pendingTasksList.remove(task)
            completedTasksList.add(task)
        } else {
            completedTasksList.remove(task)
            pendingTasksList.add(task)
        }

        _pendingTasks.value = pendingTasksList
        _completedTasks.value = completedTasksList
    }
}