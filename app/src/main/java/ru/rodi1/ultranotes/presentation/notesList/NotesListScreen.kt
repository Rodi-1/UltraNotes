package ru.rodi1.ultranotes.presentation.notesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.rodi1.ultranotes.data.local.Note
import ru.rodi1.ultranotes.presentation.components.FAB
import ru.rodi1.ultranotes.viewmodel.NotesListViewModel


@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel,
    onFabClick: () -> Unit,
    onNoteClick: (Int) -> Unit,

) {

    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val selectedNotes by viewModel.selectedNotes.collectAsState()
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        floatingActionButton = {
            if (!isSelectionMode){
            FAB(onClick = onFabClick)}
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            if (isSelectionMode) {
                SelectionBottomBar(
                    selectedCount = selectedNotes.size,
                    onCloseSelection = { viewModel.clearSelection() },
                    onDeleteSelected = { viewModel.deleteSelectedNotes() }
                )
            }
        }

    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp), // ширина плитки
            contentPadding = PaddingValues(
                start = 8.dp, end = 8.dp, top = 8.dp, bottom = 180.dp
            ),
            modifier = Modifier
                .padding(contentPadding)
        ) {
            items(notes, key = {it.id}) { note ->
                val isSelected = note.id in selectedNotes
                NoteGridItem(
                    note = note,
                    isSelectionMode = isSelectionMode,
                    isSelected = isSelected,
                    onClick = {
                        if (isSelectionMode) {
                            viewModel.toggleSelection(note.id)
                        } else {
                            onNoteClick(note.id)
                        }
                    },
                    onLongClick = {
                        viewModel.enterSelectionMode(note.id)
                    },
                )
            }
        }
    }
}

@Composable
fun SelectionBottomBar(
    selectedCount: Int,
    onCloseSelection: () -> Unit,
    onDeleteSelected: () -> Unit
) {
    BottomAppBar(
    ) {
        // Кнопка «Закрыть режим»
        IconButton(onClick = onCloseSelection) {
            Icon(Icons.Default.Close, contentDescription = "Cancel selection")
        }
        Spacer(Modifier.width(16.dp))
        Text(text = "Выбрано: $selectedCount", modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(Modifier.weight(1f)) // раздвинем вправо

        IconButton(onClick = onDeleteSelected) {
            Icon(Icons.Default.Delete, contentDescription = "Delete selected")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteGridItem(
    note: Note,
    isSelectionMode: Boolean, // используем потом
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {

    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(200.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            // Заголовок
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            // Содержимое
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
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