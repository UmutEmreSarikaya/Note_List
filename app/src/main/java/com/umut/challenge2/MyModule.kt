package com.umut.challenge2

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object MyModule {

    @Provides
    fun providesMyDao(@ApplicationContext applicationContext: Context): NoteDao{
        return Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "note-database"
        ).build().noteDao()
    }
}