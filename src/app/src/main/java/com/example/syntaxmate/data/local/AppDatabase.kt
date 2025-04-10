package com.example.syntaxmate.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.syntaxmate.data.Converters
import com.example.syntaxmate.data.model.CodeExample
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.model.SyntaxEntity
import com.example.syntaxmate.workers.SyncLanguagesWorker
import com.example.syntaxmate.workers.SyncLanguagesWorker.Companion.KEY_FILENAME
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
            val request = OneTimeWorkRequestBuilder<SyncLanguagesWorker>()
                .setInputData(workDataOf(KEY_FILENAME to "languages.json"))
                .build()

            WorkManager.getInstance(context).enqueue(request)
        }

    }

}