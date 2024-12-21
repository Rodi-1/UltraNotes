package ru.rodi1.ultranotes.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.rodi1.ultranotes.viewmodel.NotesViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val notesViewModel: NotesViewModel by viewModels()
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController, notesViewModel)
                }
           }
       }
   }
}

@Composable
fun AppNavHost(navController: NavHostController, notesViewModel: NotesViewModel) {
    NavHost(navController, startDestination = "notesList") {
        composable("notesList") {
            NotesListScreen(
                notesViewModel = notesViewModel,
                onFabClick = { navController.navigate("noteEditor") }
            )
        }
        composable("noteEditor") {
            NoteEditorScreen(
                onSaveClick = { title, content ->
                    notesViewModel.addNote()
                    navController.popBackStack()
                }
            )
        }
    }
}

