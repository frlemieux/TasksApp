package com.lemieux.tasks.domain.repository

import com.lemieux.tasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    // Function to get all tasks as a Flow
    fun getAllTasks(): Flow<List<Task>>

    // Function to insert a task
    suspend fun insertTask(task: Task)

    // Function to delete a task
    suspend fun deleteTask(task: Task)
}
