package ru.rodi1.ultranotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.rodi1.ultranotes.data.NotesRepository
import ru.rodi1.ultranotes.model.Note
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    // Используем Flow из Room
    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // 5 секунд до завершения (на случай изменения конфигурации (чтобы не перезапускалось при повороте экрана))
            initialValue = emptyList()
        )

    // Метод для добавления новой заметки
    fun addNote(title: String = "New Note", content: String = "") {
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                content = content
            )
            repository.addNote(newNote)
        }
    }

    // Метод для обновления существующей заметки
    fun updateNote(noteId: Int, title: String, content: String) {
        viewModelScope.launch {
            // Допустим, у нас есть Note, который мы хотим обновить
            // либо нам нужно сначала найти note по id
            val updatedNote = Note(
                id = noteId,
                title = title,
                content = content
            )
            repository.updateNote(updatedNote)
        }
    }

    // Метод для удаления
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}