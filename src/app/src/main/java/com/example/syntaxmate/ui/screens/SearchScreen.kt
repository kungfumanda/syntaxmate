package com.example.syntaxmate.ui.screens

// Futura tela de busca

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.syntaxmate.ui.navigation.Routes

@Composable
fun SearchScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Search Screen")

        Spacer(modifier = Modifier.height(20.dp))

        // Tô deixando aqui mesmo tendo feito a barra inferior ja pra sabermos como fazer (:
        Button(
            onClick = { navController.navigate(Routes.Favorites.route) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Go to Favorites")
        }

        Button(
            onClick = { navController.navigate(Routes.History.route) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Go to History")
        }

    }
}
