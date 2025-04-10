package com.example.syntaxmate.data.local

import androidx.room.*
import com.example.syntaxmate.data.model.SyntaxEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyntaxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyntax(syntax: SyntaxEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSyntax(syntaxList: List<SyntaxEntity>)

    @Query("SELECT * FROM syntaxes")
    fun getAllSyntax(): Flow<List<SyntaxEntity>>

    @Query("SELECT * FROM syntaxes WHERE languageName = :languageName")
    fun getSyntaxesByLanguage(languageName: String): Flow<List<SyntaxEntity>>

    @Query("SELECT * FROM syntaxes WHERE tag LIKE '%' || :tag || '%'")
    fun getSyntaxesByTag(tag: String): Flow<List<SyntaxEntity>>

    @Query("SELECT * FROM syntaxes WHERE isFavorite = 1")
    fun getFavoriteSyntaxes(): Flow<List<SyntaxEntity>>

    @Update
    suspend fun updateSyntax(syntax: SyntaxEntity)
}
