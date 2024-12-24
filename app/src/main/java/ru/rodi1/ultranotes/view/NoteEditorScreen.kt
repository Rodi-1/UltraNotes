package ru.rodi1.ultranotes.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.rodi1.ultranotes.viewmodel.NotesViewModel


@Composable
fun NoteEditorScreen(
    onBack: () -> Unit // Функция, которая будет вызываться после нажатия на кнопку, и передаваться из MainActivity
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val notesViewModel = hiltViewModel<NotesViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Поле для заголовка
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

            // Поле для содержимого заметки
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                placeholder = { Text("Enter note content") },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                maxLines = Int.MAX_VALUE,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
        }

        // Кнопка "Сохранить"
        Button(
            onClick = {
                notesViewModel.addNote(title, content)
                onBack() },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NoteEditorScreenPreview() {
//    UltraNotesTheme {
//        NoteEditorScreen(onSaveClick = { title, content ->
//            Log.d("NoteEditorScreenPreview", "Title: $title")
//            Log.d("NoteEditorScreenPreview", "Content: $content")
//        })
//    }
//}
