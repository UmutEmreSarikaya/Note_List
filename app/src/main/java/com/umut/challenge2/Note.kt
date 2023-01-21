package com.umut.challenge2

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "header") var header: String,
    @ColumnInfo(name = "text") var text: String
): Parcelable
