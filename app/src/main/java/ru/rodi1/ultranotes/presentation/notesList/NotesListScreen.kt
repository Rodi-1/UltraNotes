package ru.rodi1.ultranotes.presentation.notesList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.rodi1.ultranotes.data.local.Note
import ru.rodi1.ultranotes.presentation.components.FAB


@Composable
fun NotesListScreen(
    notes: List<Note>,
    onFabClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
    onDeleteClick: (Note) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FAB(onClick = onFabClick)
        },
        floatingActionButtonPosition = FabPosition.End

    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp), // ширина плитки
            contentPadding = PaddingValues(
                start = 8.dp, end = 8.dp, top = 8.dp, bottom = 180.dp
            ),
            modifier = Modifier
                .padding(contentPadding)
        ) {
            items(notes) { note ->
                NoteGridItem(
                    note = note,
                    onClick = { onNoteClick(note.id) },
                    onDeleteClick = { onDeleteClick(note) }
                )
            }
        }
    }
}
@Composable
fun NoteGridItem(
    note: Note,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}




//@Preview
//@Composable
//fun NotesListScreenPreview() {
//    val notes = listOf(
//        Note(
//            id = 1,
//            title = "First Note",
//            content = "This is the first note"
//        ),
//        Note(
//            id = 2,
//            title = "Second Note",
//            content = "This is the second note"
//        ),
//        Note(
//            id = 3,
//            title = "Third Note",
//            content = "This is the third note"
//        )
//    )
//    NotesListScreen(
//        notes = notes,
//        onFabClick = {},
//        onNoteClick = {},
//        onDeleteClick = {}
//    )
//}