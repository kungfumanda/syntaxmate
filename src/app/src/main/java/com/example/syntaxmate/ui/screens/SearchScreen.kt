package com.example.syntaxmate.ui.screens

// Futura tela de busca

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.syntaxmate.ui.components.*
import com.example.syntaxmate.viewmodel.LanguageViewModel

@Composable
fun SearchScreen(navController: NavController, languageViewModel: LanguageViewModel) {

    val languages by languageViewModel.languages.collectAsState(initial= emptyList())
    val selectedLanguages by languageViewModel.selectedLanguages.collectAsState(initial = emptyList())
    var languageSearch by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Programming Languages",
                style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(20.dp))

            // Barra de buscas
            OutlinedTextField(
                value = languageSearch,
                onValueChange = { languageSearch = it },
                label = { Text("Search language") },
                modifier = Modifier
                    .width(160.dp)
                    .padding(start = 8.dp),
                singleLine = true

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Linguagens disponíveis
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            val filtered = languages
                .sortedByDescending { it.isFavorite }
                .filter { it.name.contains(languageSearch, ignoreCase = true) }

            items(filtered) { language ->
                LanguageCard(
                    languageName = language.name,
                    isFavorite = language.isFavorite,
                    isSelected = selectedLanguages.any { it.name == language.name },
                    sizeType = LanguageCardSize.LARGE,
                    onFavoriteClick = { languageViewModel.toggleFavorite(language) },
                    onSelect = {
                        if (selectedLanguages.none { it.name == language.name }) {
                            languageViewModel.toggleSelected(language) } }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Linguagens selecionadas
        if (selectedLanguages.isNotEmpty()) {
            Text(text = "Selected Languages", style = MaterialTheme.typography.titleSmall)

            LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                items(selectedLanguages) { language ->
                    LanguageCard(
                        languageName = language.name,
                        isFavorite = language.isFavorite,
                        isSelected = true,
                        sizeType = LanguageCardSize.COMPACT,
                        onFavoriteClick = { languageViewModel.toggleFavorite(language) },
                        onRemoveClick = { languageViewModel.toggleSelected(language) },
                        onSelect = {
                            Log.d("HomeScreen", "Selected: ${language.name}")
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            thickness = 1.dp,
            modifier = Modifier.height(24.dp)
        )

        // Área para busca de sintaxe (estrutura base)
        Text(text = "Syntax Search", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = "", // Adicionar estado para essa busca também
            onValueChange = {},
            label = { Text("Search syntax") },
            modifier = Modifier.fillMaxWidth()
        )

        // Inserir resultados depois




//        LazyColumn {
//            itemsIndexed(languages.value) { index, language ->
//                LanguageCard(
//                    languageName = language.name,
//                    isFavorite = language.isFavorite,
//                    sizeType = LanguageCardSize.COMPACT,
//                    onFavoriteClick = { languageViewModel.toggleFavorite(language) },
//                    onSelect = {
//                        Log.d("SearchScreen", "Selected: ${language.name}")
//                    }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//
//        // Tô deixando aqui mesmo tendo feito a barra inferior ja pra sabermos como fazer (:
//        Button(
//            onClick = { navController.navigate(Routes.Favorites.route) },
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            Text(text = "Go to Favorites")
//        }
//
//        Button(
//            onClick = { navController.navigate(Routes.History.route) },
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            Text(text = "Go to History")
//        }

    }
}
