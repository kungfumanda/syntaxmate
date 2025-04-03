package com.example.syntaxmate.ui.screens

// Futura tela de busca

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.syntaxmate.ui.components.LanguageCard
import com.example.syntaxmate.ui.components.LanguageCardSize
import com.example.syntaxmate.ui.navigation.Routes
import com.example.syntaxmate.viewmodel.LanguageViewModel

@Composable
fun SearchScreen(navController: NavController, languageViewModel: LanguageViewModel) {

    val languages = languageViewModel.languages.collectAsState(initial= emptyList())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Search Screen")

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            itemsIndexed(languages.value) { index, language ->
                LanguageCard(
                    languageName = language.name,
                    isFavorite = language.isFavorite,
                    sizeType = LanguageCardSize.COMPACT,
                    onFavoriteClick = { languageViewModel.toggleFavorite(language) },
                    onSelect = {
                        Log.d("SearchScreen", "Selected: ${language.name}")
                    }
                )
            }
        }

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
