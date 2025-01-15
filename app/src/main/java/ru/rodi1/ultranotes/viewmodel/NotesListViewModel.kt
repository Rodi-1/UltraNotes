package ru.rodi1.ultranotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.rodi1.ultranotes.data.NotesRepository
import ru.rodi1.ultranotes.data.local.Note
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    // Используем Flow из Room
    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // 5 секунд до завершения (на случай изменения конфигурации (чтобы не перезапускалось при повороте экрана))
            initialValue = emptyList()
        )

    // Храним ID выбранных заметок
    private val _selectedNotes = MutableStateFlow<Set<Int>>(emptySet())
    val selectedNotes: StateFlow<Set<Int>> = _selectedNotes

    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode

    fun enterSelectionMode(noteId: Int) {
        _isSelectionMode.value = true
        _selectedNotes.value = setOf(noteId)
    }

    fun toggleSelection(noteId: Int) {
        val current = _selectedNotes.value.toMutableSet()
        if (current.contains(noteId)) {
            current.remove(noteId)
            if (current.isEmpty()) {
                // Если ничего не выбрано, выходим из режима выбора
                _isSelectionMode.value = false
            }
        } else {
            current.add(noteId)
        }
        _selectedNotes.value = current
    }

    fun clearSelection() {
        _selectedNotes.value = emptySet()
        _isSelectionMode.value = false
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch {
            val ids = _selectedNotes.value
            ids.forEach { id ->
                repository.deleteNoteById(id)
            }
            clearSelection()
        }
    }
}