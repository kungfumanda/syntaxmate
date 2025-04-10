package com.example.syntaxmate.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syntaxmate.ui.components.LanguageCard
import com.example.syntaxmate.ui.components.LanguageCardSize
import com.example.syntaxmate.ui.components.SyntaxCard
import com.example.syntaxmate.viewmodel.LanguageViewModel
import com.example.syntaxmate.viewmodel.SyntaxViewModel

@Composable
fun HomeScreen(languageViewModel: LanguageViewModel, syntaxViewModel: SyntaxViewModel) {

    val languages by languageViewModel.languages.collectAsState(initial = emptyList())
    val selectedLanguages by languageViewModel.selectedLanguages.collectAsState(initial = emptyList())
    val syntaxes by syntaxViewModel.syntaxes.collectAsState()
    var languageSearch by remember { mutableStateOf("") }
    var syntaxSearch by remember { mutableStateOf("") }

    val filteredLanguages = if (selectedLanguages.isEmpty()) {
        languages.sortedByDescending { it.isFavorite }
    } else {
        selectedLanguages
    }
    val syntaxesByLanguage = filteredLanguages.associateWith { language ->
        syntaxes.filter { it.languageName.equals(language.name, ignoreCase = true) }
            .filter {
                syntaxSearch.isBlank() ||
                        it.title.contains(syntaxSearch, ignoreCase = true) ||
                        it.title.contains(syntaxSearch, ignoreCase = true)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Programming Languages",
                style = MaterialTheme.typography.titleMedium
            )
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
                            languageViewModel.toggleSelected(language)
                        }
                    }
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
            value = syntaxSearch, // Adicionar estado para essa busca também
            onValueChange = { syntaxSearch = it },
            label = { Text("Search syntax") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            syntaxesByLanguage.forEach { (language, syntaxList) ->
                item {
                    Text(
                        text = language.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(syntaxList) { syntax ->
                    SyntaxCard(
                        syntaxTitle = syntax.title,
                        codeSnippet = syntax.code,
                        languageName = syntax.languageName,
                        isFavorite = syntax.isFavorite,
                        tags = syntax.tag,
                        onFavoriteClick = { syntaxViewModel.toggleFavorite(syntax) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}
