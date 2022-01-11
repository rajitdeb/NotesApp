package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.Constants.NOTES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = NOTES_TABLE_NAME)

data class Note(
    val noteTitle: String,
    val noteDescription: String?
): Serializable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}