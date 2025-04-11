package com.example.syntaxmate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.syntaxmate.ui.components.LanguageCard
import com.example.syntaxmate.ui.components.LanguageCardSize
import com.example.syntaxmate.ui.components.SyntaxCard
import com.example.syntaxmate.ui.navigation.Routes
import com.example.syntaxmate.viewmodel.LanguageViewModel
import com.example.syntaxmate.viewmodel.SyntaxViewModel

@Composable
fun FavoritesScreen(
    languageViewModel: LanguageViewModel,
    syntaxViewModel: SyntaxViewModel,
    navController: NavController
) {
    val languages by languageViewModel.favoriteLanguagesMutable.collectAsState(initial = emptyList())
    val syntaxes by syntaxViewModel.favoriteSyntaxes.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // --- Favorite Languages Section ---
        item {
            Text(
                text = "Favorite Languages",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (languages.isNotEmpty()) {
            items(languages) { language ->
                LanguageCard(
                    languageName = language.name,
                    isFavorite = language.isFavorite,
                    isSelected = false,
                    sizeType = LanguageCardSize.EXTRA_LARGE,
                    onFavoriteClick = { languageViewModel.toggleFavorite(language) },
                    onSelect = {}
                )
            }
        } else {
            item {
                EmptyState(
                    message = "No favorite languages yet.\nGo explore and add some!",
                    buttonText = "Browse Languages",
                    onClick = { navController.navigate(Routes.Search.route) }
                )
            }
        }

        // --- Favorite Syntaxes Section ---
        item {
            Text(
                text = "Favorite Syntaxes",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        if (syntaxes.isNotEmpty()) {
            val syntaxesByLanguage = syntaxes.groupBy { it.languageName }

            syntaxesByLanguage.forEach { (languageName, syntaxList) ->
                item {
                    Text(
                        text = languageName,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }

                items(syntaxList) { syntax ->
                    SyntaxCard(
                        syntaxTitle = syntax.title,
                        languageName = syntax.languageName,
                        isFavorite = syntax.isFavorite,
                        codeSnippet = syntax.code,
                        tags = syntax.tag,
                        onFavoriteClick = { syntaxViewModel.toggleFavorite(syntax) }
                    )
                }
            }
        } else {
            item {
                EmptyState(
                    message = "No favorite syntaxes yet.\nMark your go-to examples!",
                    buttonText = "Explore Syntaxes",
                    onClick = { navController.navigate("syntaxBrowser") }
                )
            }
        }
    }
}

@Composable
fun EmptyState(
    message: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Button(onClick = onClick) {
            Text(buttonText)
        }
    }
}
