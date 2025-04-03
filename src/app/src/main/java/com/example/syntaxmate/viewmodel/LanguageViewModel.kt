package com.example.syntaxmate.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.repository.LanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LanguageViewModel(private val repository: LanguageRepository) : ViewModel() {

    private val _languages = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val languages: StateFlow<List<LanguageEntity>> = _languages

    private val _favoriteLanguages = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val favoriteLanguages: StateFlow<List<LanguageEntity>> = _favoriteLanguages

    private val _selectedLanguages = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val selectedLanguages: StateFlow<List<LanguageEntity>> = _selectedLanguages

    init {
        loadLanguages()
        loadFavoriteLanguages()
    }

    private fun loadLanguages() {
        viewModelScope.launch {
            _languages.value = repository.getAllLanguages()
        }
    }

    private fun loadFavoriteLanguages() {
        viewModelScope.launch {
            _favoriteLanguages.value = repository.getFavoriteLanguages()
        }
    }

    fun toggleFavorite(language: LanguageEntity) {
        val updatedLanguage = language.copy(isFavorite = !language.isFavorite)
        viewModelScope.launch {
            repository.updateLanguage(updatedLanguage)
            loadFavoriteLanguages() // Recarregar as linguagens favoritas
        }
    }

    // teste
    fun populateTestLanguages() {
        val testLanguages = listOf(
            LanguageEntity(name = "Kotlin", icon = "ic_kotlin", isFavorite = false),
            LanguageEntity(name = "Python", icon = "ic_python", isFavorite = true),
            LanguageEntity(name = "JavaScript", icon = "ic_javascript", isFavorite = false)
        )

        viewModelScope.launch {
            testLanguages.forEach { repository.insertLanguage(it) }
            loadLanguages() // Recarregar as linguagens para atualizar o estado
        }
    }

    // teste
    fun checkInsertedLanguages() {
        viewModelScope.launch {
            val allLanguages = repository.getAllLanguages()
            allLanguages.forEach { language ->
                Log.d("LanguageViewModel", "Language: ${language.name}")
            }
        }
    }

}