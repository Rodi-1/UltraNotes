package ru.rodi1.ultranotes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.rodi1.ultranotes.model.Note
import ru.rodi1.ultranotes.viewmodel.NotesViewModel


@Composable
fun NotesListScreen(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit
) {
    val notesViewModel = hiltViewModel<NotesViewModel>()
    val notes = notesViewModel.notes.collectAsState().value
    Scaffold(
        floatingActionButton = {
            FAB(onClick = onFabClick)
        },
        floatingActionButtonPosition = FabPosition.End

    ) { contentPadding ->
        LazyColumn(
            modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            items(notes) { note ->
                NoteItem(note = note, onClick = { /* TODO: Handle note click */ })
            }

        }
    }
}
@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    // Implement UI for displaying a single note item
    // For example:
    Text(
        text = note.title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}



//@Preview
//@Composable
//fun NotesListScreenPreview() {
//    NotesListScreen(
//        notesViewModel = NotesViewModel(),
//        onFabClick = {}
//    )
//}