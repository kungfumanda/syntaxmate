package com.example.syntaxmate.data.model

import androidx.compose.ui.graphics.painter.Painter


data class Language(
    val name: String,
    val icon: Painter,
    val isFavorite: Boolean = false
)
