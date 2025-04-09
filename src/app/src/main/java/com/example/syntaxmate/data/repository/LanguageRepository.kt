package com.example.syntaxmate.data.repository

import android.content.Context
import com.example.syntaxmate.data.local.AppDatabase
import com.example.syntaxmate.data.model.LanguageEntity
import kotlinx.coroutines.flow.Flow

class LanguageRepository(context: Context) {

    private val appDatabase: AppDatabase = AppDatabase.getDatabase(context)

    private val languageDao = appDatabase.languageDao()

    fun getAllLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getAllLanguages()
    }

    fun getFavoriteLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getFavoriteLanguages()
    }

    suspend fun insertLanguage(language: LanguageEntity) {
        languageDao.insert(language)
    }

    suspend fun updateLanguage(language: LanguageEntity) {
        languageDao.update(language)
    }
}