package ru.rodi1.ultranotes.presentation.noteEditor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.rodi1.ultranotes.ui.theme.UltraNotesTheme
import ru.rodi1.ultranotes.viewmodel.NoteEditorViewModel

//TODO+
// Нужно будет передавать заголовок и контент из вне, чтобы отображать уже созданную заметку из бд.
// При этом нужны дефолтные значения для случая создания новой заметки


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    viewModel: NoteEditorViewModel,
    onBack: () -> Unit
) {
    val note by viewModel.note.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // Обновляем поля при изменении заметки
    LaunchedEffect(note) {
        title = note?.title ?: ""
        content = note?.content ?: ""
    }

    // --- DEBOUNCE-логика автосохранения ---
    LaunchedEffect(title, content) {
        kotlinx.coroutines.delay(500)
        viewModel.saveNote(title, content)
    }

    // --- Обработка быстрого выхода (например, Back) ---
    DisposableEffect(Unit) {
        onDispose {
            // При закрытии (или уничтожении) этого Composable вызываем дополнительное сохранение
            viewModel.saveNote(title, content)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (note != null) "Edit Note" else "New Note") }, //TODO: Поменять на название заметки (или как-то иначе сделать красивее)
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveNote(title, content)
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Enter note title") },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                placeholder = { Text("Enter note content") },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                maxLines = Int.MAX_VALUE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteEditorScreenPreview() {
    UltraNotesTheme {
        NoteEditorScreen(hiltViewModel(), {})
    }
}
