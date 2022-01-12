package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.HomeScreen
import com.example.myapplication.route.Routes
import com.example.myapplication.ui.screens.addnote.AddNotesScreen
import com.example.myapplication.ui.screens.editnote.EditNoteScreen
import com.example.myapplication.viewmodel.NotesViewModel

@Composable
fun Navigation(notesViewModel: NotesViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN
    ) {

        composable(Routes.HOME_SCREEN) {
            HomeScreen(notesViewModel = notesViewModel, navController = navController)
        }

        composable(Routes.ADD_NOTES_SCREEN) {
            AddNotesScreen(notesViewModel = notesViewModel, navController = navController)
        }

        composable(
            route = "${Routes.EDIT_NOTE_SCREEN}/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            val noteId = it.arguments?.getInt("noteId")!!
            notesViewModel.getNoteById(noteId)
            val actualNote = notesViewModel.getNote.observeAsState().value
            if(actualNote != null) {
                EditNoteScreen(
                    noteToUpdate = actualNote,
                    notesViewModel = notesViewModel,
                    navController = navController
                )
            }

        }
    }
}