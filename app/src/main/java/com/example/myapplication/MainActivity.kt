package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.model.Note
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.NotesViewModel
import com.example.myapplication.viewmodel.NotesViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notesViewModel by viewModels<NotesViewModel> {
            NotesViewModelFactory((this.applicationContext as MyApplication).repository)
        }
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen(notesViewModel = notesViewModel)
            }
        }
    }
}

@Composable
fun MyFabButton(notesViewModel: NotesViewModel) {
    FloatingActionButton(
        backgroundColor = Color.Red,
        onClick = { addNote(notesViewModel) }
    ) {
        Icon(Icons.Filled.Add, "Add New Note")
    }
}

private fun addNote(notesViewModel: NotesViewModel) {
    notesViewModel.addNewNote(Note("Some Title #1", ("hello World").repeat(96)))
}

@Composable
fun HomeScreen(notesViewModel: NotesViewModel) {
    Scaffold(
        floatingActionButton = {
            MyFabButton(notesViewModel = notesViewModel)
        }
    ) {
        NotesList(notesViewModel = notesViewModel)
    }
}

@Composable
fun NotesList(notesViewModel: NotesViewModel) {
    val listOfNotes = notesViewModel.getAllNotes.observeAsState(listOf())

    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(items = listOfNotes.value) { note ->
            NoteItemView(note = note, notesViewModel = notesViewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteItemView(note: Note, notesViewModel: NotesViewModel) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = { notesViewModel.deleteNote(note) }
            )
    ) {
        Row(modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
            .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.noteTitle,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )

                if (expanded) {
                    Text(
                        text = note.noteDescription ?: "No description found",
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Normal
                    )
                } else {
                    Text(
                        text = note.noteDescription ?: "No description found",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            if(note.noteDescription != null) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        tint = MaterialTheme.colors.onPrimary,
                        imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if(expanded) {
                            stringResource(id = R.string.show_less)
                        } else {
                            stringResource(id = R.string.show_more)
                        }
                    )
                }
            }
        }
    }
}