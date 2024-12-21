package ru.rodi1.ultranotes.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun NotesListScreen(modifier: Modifier = Modifier) {
    Scaffold (
        floatingActionButton = {
            FAB()
        },
        floatingActionButtonPosition = FabPosition.End

    ){  contentPadding ->
        LazyColumn(
            modifier
                .padding(contentPadding)
                .fillMaxSize()

        )
        {

        }

    }
}

@Preview
@Composable
fun NotesListScreenPreview() {
    NotesListScreen()
}