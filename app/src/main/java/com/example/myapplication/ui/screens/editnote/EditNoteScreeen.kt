package com.example.myapplication.ui.screens.editnote

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.model.Note
import com.example.myapplication.routes.Route
import com.example.myapplication.viewmodel.NotesViewModel

@Composable
fun EditNoteScreen(
    noteToUpdate: Note?,
    notesViewModel: NotesViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    var noteTitleText by remember {
        mutableStateOf(noteToUpdate?.noteTitle ?: "")
    }

    var noteDescriptionText by remember {
        mutableStateOf(noteToUpdate?.noteDescription ?: "")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextField(
                label = { Text(text = "Note Title", fontWeight = FontWeight.Bold) },
                value = noteTitleText,
                onValueChange = { noteTitleText = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                label = { Text(text = "Note Description", fontWeight = FontWeight.Bold) },
                value = noteDescriptionText,
                onValueChange = {
                    noteDescriptionText = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                noteToUpdate?.noteTitle = noteTitleText
                noteToUpdate?.noteDescription = noteDescriptionText

                notesViewModel.updateNote(noteToUpdate!!)
                Toast.makeText(
                    context,
                    "Note Updated",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(Route.HOME_SCREEN)
            }) {
                Text(text = "Update Note")
            }

        }
    }
}
