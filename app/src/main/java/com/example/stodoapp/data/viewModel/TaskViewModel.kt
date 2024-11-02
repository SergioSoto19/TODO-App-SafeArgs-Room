package com.example.stodoapp.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stodoapp.data.db.TaskDatabase
import com.example.stodoapp.data.models.Task
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val database: TaskDatabase = TaskDatabase.getDatabase(application)
    val pendingTasks: LiveData<List<Task>> = database.taskDao().getPendingTasks()
    val completedTasks: LiveData<List<Task>> = database.taskDao().getCompletedTasks()


    fun addTask(task: Task) {
        viewModelScope.launch {
            database.taskDao().insertTask(task)
        }
    }

    fun updateTaskCompletion(task: Task) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        viewModelScope.launch {
            database.taskDao().updateTask(updatedTask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            database.taskDao().deleteTask(task)
        }
    }
}