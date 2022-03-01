package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModal(app: Application) : AndroidViewModel(app) {
    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao: NotesDao = NoteDatabase.getDatabase(app).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
}