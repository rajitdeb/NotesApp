package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.model.Note
import com.example.myapplication.navigation.Navigation
import com.example.myapplication.route.Routes
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
                Navigation(notesViewModel = notesViewModel)
            }
        }
    }
}

@Composable
fun MyFabButton(navController: NavHostController) {
    FloatingActionButton(
        onClick = { navController.navigate(Routes.ADD_NOTES_SCREEN) }
    ) {
        Icon(Icons.Filled.Add, "Add New Note")
    }
}

@Composable
fun HomeScreen(notesViewModel: NotesViewModel, navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            MyFabButton(navController)
        }
    ) {
        NotesList(notesViewModel, navController)
    }
}

@Composable
fun NotesList(notesViewModel: NotesViewModel, navController: NavHostController) {
    val listOfNotes = notesViewModel.getAllNotes.observeAsState(listOf())

    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(items = listOfNotes.value) { note ->
            NoteItemView(note = note, notesViewModel = notesViewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteItemView(note: Note, notesViewModel: NotesViewModel, navController: NavHostController) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = { navController.navigate("${Routes.EDIT_NOTE_SCREEN}/${note.id}") },
                onLongClick = { notesViewModel.deleteNote(note) }
            )
    ) {
        Row(modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
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