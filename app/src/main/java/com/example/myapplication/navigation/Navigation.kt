package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.HomeScreen
import com.example.myapplication.data.model.Note
import com.example.myapplication.routes.Route
import com.example.myapplication.ui.screens.addnote.AddNotesScreen
import com.example.myapplication.ui.screens.editnote.EditNoteScreen
import com.example.myapplication.viewmodel.NotesViewModel

@Composable
fun Navigation(notesViewModel: NotesViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME_SCREEN
    ) {

        composable(Route.HOME_SCREEN) {
            HomeScreen(notesViewModel = notesViewModel, navController = navController)
        }

        composable(Route.ADD_NOTE_SCREEN) {
            AddNotesScreen(notesViewModel = notesViewModel, navController = navController)
        }
    }
}