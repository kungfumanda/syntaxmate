package com.example.syntaxmate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.syntaxmate.ui.screens.FavoritesScreen
import com.example.syntaxmate.ui.screens.HomeScreen
import com.example.syntaxmate.viewmodel.LanguageViewModel
import com.example.syntaxmate.viewmodel.SyntaxViewModel


@Composable
fun AppNavigation(navController: NavHostController, languageViewModel: LanguageViewModel, syntaxViewModel: SyntaxViewModel) {

   Scaffold (
       bottomBar = { BottomNavigationBar(navController) }
   ) { innerPadding -> NavHost(
       navController = navController,
       startDestination = "search",
       modifier = Modifier.padding(innerPadding)
   ) {
        composable("search") { HomeScreen(languageViewModel, syntaxViewModel) }
        composable("favorites") { FavoritesScreen(languageViewModel,syntaxViewModel,navController) }
    } }
}

