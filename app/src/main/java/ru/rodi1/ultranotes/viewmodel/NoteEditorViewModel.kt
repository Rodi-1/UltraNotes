package ru.rodi1.ultranotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.rodi1.ultranotes.data.NotesRepository
import ru.rodi1.ultranotes.data.local.Note
import javax.inject.Inject

@HiltViewModel
class NoteEditorViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            repository.getNoteById(noteId).collect { fetchedNote ->
                _note.value = fetchedNote
            }
        }
    }

    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            val currentNote = _note.value
            if (currentNote != null) {
                // UPDATE
                val updatedNote = currentNote.copy(title = title, content = content)
                repository.updateNote(updatedNote)
                _note.value = updatedNote
            } else {
                // INSERT
                val newNote = Note(title = title, content = content)
                val generatedId = repository.addNote(newNote)

                // Теперь _note.value уже не null
                _note.value = newNote.copy(id = generatedId.toInt())
            }
        }
    }
}
