package ru.rodi1.ultranotes.data

import kotlinx.coroutines.flow.Flow
import ru.rodi1.ultranotes.model.Note
import ru.rodi1.ultranotes.model.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotesFlow()

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}