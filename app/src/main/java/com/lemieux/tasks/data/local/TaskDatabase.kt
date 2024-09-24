package com.lemieux.tasks.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // Use Flow to observe task list changes
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Insert a new task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskEntity: TaskEntity)

    // Delete a task
    @Query("DELETE FROM task_table WHERE taskName = :taskName")
    suspend fun delete(taskName: String)
}

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
