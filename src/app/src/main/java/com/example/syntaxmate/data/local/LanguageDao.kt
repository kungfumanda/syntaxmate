package com.example.syntaxmate.data.local

import androidx.room.*
import com.example.syntaxmate.data.model.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: LanguageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<LanguageEntity>)

    @Update
    suspend fun update(language: LanguageEntity)

    @Query("SELECT * FROM languages")
    fun getAllLanguages(): Flow<List<LanguageEntity>>

    @Query("SELECT * FROM languages WHERE isFavorite = 1")
    fun getFavoriteLanguages(): Flow<List<LanguageEntity>>
}
