package com.example.syntaxmate.data.repository

import android.content.Context
import androidx.room.Room
import com.example.syntaxmate.data.local.AppDatabase
import com.example.syntaxmate.data.local.LanguageDao
import com.example.syntaxmate.data.model.LanguageEntity
import kotlinx.coroutines.flow.Flow

class LanguageRepository(context: Context) {

    private val appDatabase: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "language-database"
    ).build()

    private val languageDao = appDatabase.languageDao()

    suspend fun getAllLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getAllLanguages()
    }

    suspend fun getFavoriteLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getFavoriteLanguages()
    }

    suspend fun insertLanguage(language: LanguageEntity) {
        languageDao.insert(language)
    }

    suspend fun updateLanguage(language: LanguageEntity) {
        languageDao.update(language)
    }
}