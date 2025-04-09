package com.example.syntaxmate.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.syntaxmate.data.model.LanguageEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

@Database(entities = [LanguageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .fallbackToDestructiveMigration() // ta aqui so pra teste -> se mudou ele destrói e cria outro
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("PopulateDB", "Iniciando BD")
            // roda num thread separado
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    populateDatabase(context, database)
                }
            }
        }


        private suspend fun populateDatabase(context: Context, database: AppDatabase) {

            Log.d("PopulateDB", "Iniciando leitura do JSON")

            val json = context.assets.open("languages.json").bufferedReader().use { it.readText() }
            Log.d("PopulateDB", "JSON lido com sucesso")

            val gson = Gson()
            val listType = object : TypeToken<List<LanguageEntity>>() {}.type
            val languages: List<LanguageEntity> = gson.fromJson(json, listType)

            database.languageDao().insertAll(languages)
            Log.d("PopulateDB", "Dados inseridos com sucesso")
        }
    }

}