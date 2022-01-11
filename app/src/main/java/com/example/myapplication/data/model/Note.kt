package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.Constants.NOTES_TABLE_NAME

@Entity(tableName = NOTES_TABLE_NAME)
data class Note(
    val noteTitle: String,
    val noteDescription: String?
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}