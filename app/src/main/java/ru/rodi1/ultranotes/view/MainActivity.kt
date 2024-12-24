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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

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
        navController,
        startDestination = "notesList"
    ) {
        composable("notesList") {

            NotesListScreen(
                onFabClick = { navController.navigate("noteEditor") }
            )
        }
        composable("noteEditor") {
            NoteEditorScreen(
                onBack = {navController.popBackStack() }
            )
        }
    }
}

