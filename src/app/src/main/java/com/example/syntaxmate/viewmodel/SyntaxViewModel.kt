package com.example.syntaxmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.model.SyntaxEntity
import com.example.syntaxmate.data.repository.SyntaxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyntaxViewModel(private val repository: SyntaxRepository) :ViewModel() {

    private val _syntaxes = MutableStateFlow<List<SyntaxEntity>>(emptyList())
    val syntaxes: StateFlow<List<SyntaxEntity>> = _syntaxes

    private val _favoriteSyntaxes = MutableStateFlow<List<SyntaxEntity>>(emptyList())
    val favoriteSyntaxes: StateFlow<List<SyntaxEntity>> = _favoriteSyntaxes


    init {
        loadSyntaxes()
        loadFavoriteSyntaxes()
    }

    private fun loadSyntaxes() {
        viewModelScope.launch {
            repository.getAllSyntaxes().collect { syntaxes ->
                _syntaxes.value = syntaxes
            }
        }
    }

    private fun loadFavoriteSyntaxes() {
        viewModelScope.launch {
            repository.getFavoriteSyntaxes().collect { favoriteSyntaxes ->
                _favoriteSyntaxes.value = favoriteSyntaxes
            }
        }
    }

    fun toggleFavorite(syntax: SyntaxEntity) {
        val updatedSyntax = syntax.copy(isFavorite = !syntax.isFavorite)
        viewModelScope.launch {
            repository.updateSyntax(updatedSyntax)
            loadFavoriteSyntaxes() // Recarregar as linguagens favoritas
        }
    }
}