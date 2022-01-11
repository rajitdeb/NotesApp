package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.NoteDao
import com.example.myapplication.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val getAllNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    //insert a new note
    suspend fun addNewNote(note: Note) {
        noteDao.addNote(note)
    }

    // delete a note
    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }


}