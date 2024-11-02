package com.example.stodoapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stodoapp.data.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    fun getPendingTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1")
    fun getCompletedTasks(): LiveData<List<Task>>
}