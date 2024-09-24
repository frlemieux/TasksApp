package com.lemieux.tasks.data.di

import android.content.Context
import androidx.room.Room
import com.lemieux.tasks.data.local.TaskDao
import com.lemieux.tasks.data.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context,
    ): TaskDatabase =
        Room
            .databaseBuilder(
                context,
                TaskDatabase::class.java,
                "tasks.db",
            ).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()
}
