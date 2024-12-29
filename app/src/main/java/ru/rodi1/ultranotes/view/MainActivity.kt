package ru.rodi1.ultranotes.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.rodi1.ultranotes.viewmodel.NoteEditorViewModel
import ru.rodi1.ultranotes.viewmodel.NotesListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController)
                }
           }
       }
        Log.i("MainActivity", "onCreate: Контент задан")
   }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "notesList"
    ) {
        composable("notesList") {
            val notesListViewModel: NotesListViewModel = hiltViewModel()
            val notes by notesListViewModel.notes.collectAsState()

            NotesListScreen(
                notes = notes,
                onFabClick = { navController.navigate("noteEditor") },
                onNoteClick = { noteId ->
                    navController.navigate("noteEditor/$noteId")
                },
                onDeleteClick = { note ->
                    notesListViewModel.deleteNote(note)
                }
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
                // Загрузка заметки по ID
                noteEditorViewModel.loadNote(noteId)
            }
            NoteEditorScreen(
                viewModel = noteEditorViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

