package com.example.syntaxmate.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.repository.LanguageRepository
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
            repository.getAllLanguages().collect({ languages ->
                _languages.value = languages
            })
        }
    }

    private fun loadFavoriteLanguages() {
        viewModelScope.launch {
            repository.getFavoriteLanguages().collect( { favoriteLanguages ->
            _favoriteLanguages.value = favoriteLanguages
            })
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
            repository.getAllLanguages().collect({ languages ->
                languages.forEach { language ->
                    Log.d("LanguageViewModel", "Language: ${language.name}" )}
            })
            }
        }
    }