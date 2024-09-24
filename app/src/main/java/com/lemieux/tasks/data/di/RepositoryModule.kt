package com.lemieux.tasks.data.di

import com.lemieux.tasks.data.repository.TaskRepositoryImpl
import com.lemieux.tasks.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
}
