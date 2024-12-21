package ru.rodi1.ultranotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rodi1.ultranotes.model.Note

class NotesViewModel : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>(emptyList())
    val notes: LiveData<List<Note>> = _notes

    private var nextId = 0

    fun addNote() {
        val newNote = Note(
            id = nextId++,
            title = "New Note",
            content = ""
        )
        _notes.value = _notes.value?.plus(newNote)
    }

    fun updateNoteContent(id: Int, title: String, content: String) {
        _notes.value = _notes.value?.map {
            if (it.id == id) it.copy(title = title, content = content) else it
        }
    }
}