package com.example.syntaxmate.data.model
import androidx.room.*


@Entity(
    tableName = "syntaxes",
     foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["name"],
            childColumns = ["languageName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["languageName"])]
)
data class SyntaxEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val languageName: String,
    val title: String,
    val code: String,
    val isFavorite: Boolean = false,
    val tag: List<String>,
)
