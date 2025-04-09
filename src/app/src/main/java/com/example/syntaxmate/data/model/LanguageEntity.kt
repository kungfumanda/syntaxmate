package com.example.syntaxmate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey val name: String,
    val icon: String,
    val isFavorite: Boolean = false,
    val paradigm: String,
    val typing: String,
    val examples: String
    // val examples: List<CodeExampleJson>
)

data class CodeExample(
    val title: String,
    val code: String
)
