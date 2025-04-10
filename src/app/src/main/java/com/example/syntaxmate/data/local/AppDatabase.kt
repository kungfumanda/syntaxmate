package com.example.syntaxmate.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.syntaxmate.data.Converters
import com.example.syntaxmate.data.model.CodeExample
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.model.SyntaxEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

@Database(entities = [LanguageEntity::class, SyntaxEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao
    abstract fun syntaxDao(): SyntaxDao

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
            val gson = Gson()

            val json = context.assets.open("languages.json").bufferedReader().use { it.readText() }
            val languagesType = object : TypeToken<List<LanguageEntity>>() {}.type
            val languages: List<LanguageEntity> = gson.fromJson(json, languagesType)
            Log.d("PopulateDB", "JSON lido com sucesso")


            database.languageDao().insertAll(languages)
            Log.d("PopulateDB", "Linguages inseridas com sucesso")

            val syntaxList = mutableListOf<SyntaxEntity>()
            for (language in languages) {
                val examplesType = object: TypeToken<List<CodeExample>>() {}.type
                val examples: List<CodeExample> = gson.fromJson(language.examples, examplesType)

                val syntaxes = examples.map { example ->
                    SyntaxEntity(
                        title = example.title,
                        code = example.code,
                        languageName = language.name,
                        isFavorite = false,
                        tag = emptyList() // add depois
                    )
                }

                syntaxList.addAll(syntaxes)

            }

            database.syntaxDao().insertAllSyntax(syntaxList)
            Log.d("PopulateDB", "Sintaxes inseridas com sucesso")


        }


    }

}