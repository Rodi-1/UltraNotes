package ru.rodi1.ultranotes.presentation.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.rodi1.ultranotes.presentation.noteEditor.NoteEditorScreen
import ru.rodi1.ultranotes.presentation.notesList.NotesListScreen
import ru.rodi1.ultranotes.viewmodel.NoteEditorViewModel
import ru.rodi1.ultranotes.viewmodel.NotesListViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "notesList"
    ) {
        composable("notesList") {
            val notesListViewModel: NotesListViewModel = hiltViewModel()
            NotesListScreen(
                viewModel = notesListViewModel,

                onFabClick = { navController.navigate("noteEditor") },
                onNoteClick = { noteId ->
                    navController.navigate("noteEditor/$noteId")
                },
            )
        }
        composable("noteEditor") {
            val noteEditorViewModel: NoteEditorViewModel = hiltViewModel()
            NoteEditorScreen(
                viewModel = noteEditorViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("noteEditor/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            val noteEditorViewModel: NoteEditorViewModel = hiltViewModel()
            if (noteId != null) {
                noteEditorViewModel.loadNote(noteId)
            }
            NoteEditorScreen(
                viewModel = noteEditorViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
