package com.umut.challenge2

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): MutableList<Note>

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note?)
}


