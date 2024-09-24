package com.lemieux.tasks.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lemieux.tasks.domain.model.Task
import com.lemieux.tasks.domain.repository.TaskRepository
import com.lemieux.tasks.ui.model.TaskUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NEW_TASK_NAME = "newTaskName"

@HiltViewModel
class TaskViewModel
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val newTaskNameValue = savedStateHandle.getStateFlow(NEW_TASK_NAME, "")

        val uiState: StateFlow<TaskUiState> =
            combine(
                taskRepository.getAllTasks().map { it.map(Task::toTaskUi) },
                newTaskNameValue,
                ::map,
            ).flowOn(Dispatchers.Default)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = TaskUiState.Success("", emptyList<TaskUi>().toPersistentList()),
                )

        private fun map(
            list: List<TaskUi>,
            newTaskName: String,
        ): TaskUiState = TaskUiState.Success(newTaskName, list.toPersistentList())

        fun deleteTask(task: TaskUi) {
            viewModelScope.launch {
                taskRepository.deleteTask(task.toTask())
            }
        }

        fun addTask() {
            if (newTaskNameValue.value.isEmpty()) return
            viewModelScope.launch {
                taskRepository.insertTask(Task(newTaskNameValue.value))
                savedStateHandle[NEW_TASK_NAME] = ""
            }
        }

        fun onValueChange(value: String) {
            savedStateHandle[NEW_TASK_NAME] = value
        }
    }

private fun Task.toTaskUi(): TaskUi = TaskUi(name = name)

private fun TaskUi.toTask(): Task = Task(name = name)

sealed class TaskUiState {
    data class Success(
        val newTask: String,
        val tasks: ImmutableList<TaskUi>,
    ) : TaskUiState()
}
