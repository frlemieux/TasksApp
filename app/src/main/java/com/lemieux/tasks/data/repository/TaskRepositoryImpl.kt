package com.lemieux.tasks.data.repository

import com.lemieux.tasks.data.local.TaskDao
import com.lemieux.tasks.data.local.TaskEntity
import com.lemieux.tasks.data.local.toTask
import com.lemieux.tasks.domain.model.Task
import com.lemieux.tasks.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl
    @Inject
    constructor(
        private val taskDao: TaskDao,
    ) : TaskRepository {
        override fun getAllTasks(): Flow<List<Task>> =
            flow {
                withContext(Dispatchers.IO) {
                    taskDao.getAllTasks().map { it.map(TaskEntity::toTask) }.collect {
                        withContext(Dispatchers.Default) {
                            emit(it)
                        }
                    }
                }
            }

        override suspend fun insertTask(task: Task) {
            withContext(Dispatchers.IO) {
                taskDao.insert(task.toEntity())
            }
        }

        override suspend fun deleteTask(task: Task) {
            withContext(Dispatchers.IO) {
                taskDao.delete(task.toEntity().taskName)
            }
        }
    }

fun Task.toEntity() = TaskEntity(taskName = name)
