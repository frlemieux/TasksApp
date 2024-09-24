package com.lemieux.tasks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lemieux.tasks.domain.model.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
)

fun TaskEntity.toTask() =
    Task(
        name = taskName,
    )
