package ru.rodi1.ultranotes.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.rodi1.ultranotes.R

@Composable
fun FAB(onClick: () -> Unit) { // Плавающая кнопка добавления заметки
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = null
        )
    }
}