package com.example.syntaxmate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.syntaxmate.ui.screens.FavoritesScreen
import com.example.syntaxmate.ui.screens.HistoryScreen
import com.example.syntaxmate.ui.screens.SearchScreen


@Composable
fun AppNavigation(navController: NavHostController) {
   Scaffold (
       bottomBar = { BottomNavigationBar(navController) }
   ) { innerPadding -> NavHost(
       navController = navController,
       startDestination = "search",
       modifier = Modifier.padding(innerPadding)
   ) {
        composable("search") { SearchScreen(navController) }
        composable("favorites") { FavoritesScreen(navController) }
        composable("history") { HistoryScreen(navController) }
    } }
}

