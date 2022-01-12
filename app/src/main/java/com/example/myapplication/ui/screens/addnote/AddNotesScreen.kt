package com.example.myapplication.ui.screens.addnote

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.model.Note
import com.example.myapplication.route.Routes
import com.example.myapplication.viewmodel.NotesViewModel

@Composable
fun AddNotesScreen(
    notesViewModel: NotesViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    var noteTitleText by remember {
        mutableStateOf("")
    }

    var noteDescriptionText by remember {
        mutableStateOf("")
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

            Button(
                onClick = {
               val note = Note(
                   noteTitleText,
                   noteDescriptionText
               )
                notesViewModel.addNewNote(note)
                Toast.makeText(
                    context,
                    "Note Saved",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(Routes.HOME_SCREEN)
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save Note")
            }

        }
    }

}