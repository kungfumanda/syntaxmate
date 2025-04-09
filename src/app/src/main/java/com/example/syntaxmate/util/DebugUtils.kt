package com.example.syntaxmate.util

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.repository.LanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DebugUtils {

    fun populateTestLanguages(repository: LanguageRepository) {
        val testLanguages = listOf(
            LanguageEntity(
                name = "Kotlin",
                icon = "ic_kotlin",
                isFavorite = false,
                paradigm = "OOP",
                typing = "Static",
                examples = "..."
            ),
            LanguageEntity(
                name = "Python",
                icon = "ic_python",
                isFavorite = true,
                paradigm = "Multi-paradigm",
                typing = "Dynamic",
                examples = "..."
            ),
            LanguageEntity(
                name = "JavaScript",
                icon = "ic_javascript",
                isFavorite = false,
                paradigm = "Event-driven",
                typing = "Dynamic",
                examples = "..."
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            testLanguages.forEach {
                repository.insertLanguage(it)
            }
        }
    }

    fun logInsertedLanguages(repository: LanguageRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllLanguages().collect { languages ->
                languages.forEach {
                    Log.d("DebugUtils", "Language: ${it.name}, Favorite: ${it.isFavorite}")
                }
            }
        }
    }
}