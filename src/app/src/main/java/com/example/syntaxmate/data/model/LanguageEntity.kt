package com.example.syntaxmate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey val name: String,
    val icon: String,
    val isFavorite: Boolean = false,
)
