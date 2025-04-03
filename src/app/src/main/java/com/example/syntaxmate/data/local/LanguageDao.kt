package com.example.syntaxmate.data.local

import androidx.room.*
import com.example.syntaxmate.data.model.LanguageEntity

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: LanguageEntity)

    @Update
    suspend fun update(language: LanguageEntity)

    @Query("SELECT * FROM languages")
    suspend fun getAllLanguages(): List<LanguageEntity>

    @Query("SELECT * FROM languages WHERE isFavorite = 1")
    suspend fun getFavoriteLanguages(): List<LanguageEntity>
}
