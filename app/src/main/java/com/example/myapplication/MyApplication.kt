package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.database.NotesDatabase
import com.example.myapplication.repository.NoteRepository

class MyApplication: Application() {
    val database by lazy { NotesDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.notesDao()) }

}