package com.example.syntaxmate.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.repository.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LanguageViewModel(private val repository: LanguageRepository) : ViewModel() {

    private val _languages = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val languages: StateFlow<List<LanguageEntity>> = _languages

     val favoriteLanguagesMutable = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val favoriteLanguages: StateFlow<List<LanguageEntity>> = favoriteLanguagesMutable

    private val _selectedLanguages = MutableStateFlow<List<LanguageEntity>>(emptyList())
    val selectedLanguages: StateFlow<List<LanguageEntity>> = _selectedLanguages

    init {
        loadLanguages()
        loadFavoriteLanguages()
    }

    private fun loadLanguages() {
        viewModelScope.launch {
            repository.getAllLanguages().collect { languages ->
                _languages.value = languages
            }
        }
    }

    private fun loadFavoriteLanguages() {
        viewModelScope.launch {
            repository.getFavoriteLanguages().collect { favoriteLanguages ->
                favoriteLanguagesMutable.value = favoriteLanguages
            }
        }
    }

    fun toggleFavorite(language: LanguageEntity) {
        val updatedLanguage = language.copy(isFavorite = !language.isFavorite)
        viewModelScope.launch {
            repository.updateLanguage(updatedLanguage)
            loadFavoriteLanguages() // Recarregar as linguagens favoritas
        }
    }

    fun toggleSelected(language: LanguageEntity) {
        val current = _selectedLanguages.value.toMutableList()

        if (current.contains(language)) {
            current.remove(language)
        } else {
            current.add(language)
        }
        _selectedLanguages.value = current
    }
}