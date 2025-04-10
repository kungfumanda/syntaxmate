package com.example.syntaxmate.data.repository

import android.content.Context
import com.example.syntaxmate.data.local.AppDatabase
import com.example.syntaxmate.data.model.SyntaxEntity
import kotlinx.coroutines.flow.Flow

class SyntaxRepository(context: Context) {

    private val appDatabase: AppDatabase = AppDatabase.getDatabase(context)

    private val syntaxDao = appDatabase.syntaxDao()


    fun getAllSyntaxes(): Flow<List<SyntaxEntity>> {
        return syntaxDao.getAllSyntax()
    }

    suspend fun getSyntaxesByLanguage(languageName: String): Flow<List<SyntaxEntity>> {
        return syntaxDao.getSyntaxesByLanguage(languageName)
    }

    suspend fun getSyntaxesByTag(tag: String): Flow<List<SyntaxEntity>> {
        return syntaxDao.getSyntaxesByTag(tag)
    }

    suspend fun getFavoriteSyntaxes(): Flow<List<SyntaxEntity>> {
        return syntaxDao.getFavoriteSyntaxes()
    }

    suspend fun insertSyntax(syntax: SyntaxEntity) {
        syntaxDao.insertSyntax(syntax)
    }

    suspend fun insertAllSyntax(syntaxList: List<SyntaxEntity>) {
        syntaxDao.insertAllSyntax(syntaxList)
    }

    suspend fun updateSyntax(syntax: SyntaxEntity) {
        syntaxDao.updateSyntax(syntax)
    }
}