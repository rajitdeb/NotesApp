package com.example.myapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.Constants.NOTES_TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = NOTES_TABLE_NAME)

@Parcelize
data class Note(
    var noteTitle: String,
    var noteDescription: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}