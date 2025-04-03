package com.example.syntaxmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.syntaxmate.data.model.LanguageEntity

@Database(entities = [LanguageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao
}