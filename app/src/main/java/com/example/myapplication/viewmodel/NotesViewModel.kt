package com.example.myapplication.viewmodel

import androidx.lifecycle.*
import com.example.myapplication.MyApplication
import com.example.myapplication.data.model.Note
import com.example.myapplication.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository
    ): ViewModel() {

    val getAllNotes: LiveData<List<Note>> = noteRepository.getAllNotes

    fun addNewNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.addNewNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(note)
    }

}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}