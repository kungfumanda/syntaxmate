package com.example.syntaxmate.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.syntaxmate.data.local.AppDatabase
import com.example.syntaxmate.data.model.CodeExample
import com.example.syntaxmate.data.model.LanguageEntity
import com.example.syntaxmate.data.model.SyntaxEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncLanguagesWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val gson = Gson()

            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                val json = applicationContext.assets.open(filename).bufferedReader().use { it.readText() }

                val languagesType = object : TypeToken<List<LanguageEntity>>() {}.type
                val languages: List<LanguageEntity> = gson.fromJson(json, languagesType)

                Log.d("PopulateDB", "JSON lido com sucesso")

                val database = AppDatabase.getDatabase(applicationContext)

                if (database.languageDao().getCount() > 0) {
                    Log.d(TAG, "Database already populated. Skipping.")
                    return@withContext Result.success()
                }

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

                Result.success()
            } else {
                Log.e(TAG,
                    "Erro ao preencher o database - nome de arquivo invalido"
                )
                Result.failure()
            }
        } catch (ex : Exception){
            Log.e(TAG, "Erro preenchendo o database", ex)
            Result.failure()
        }

    }

    companion object {
        private const val TAG = "SyncLanguagesWorker"
        const val KEY_FILENAME = "languages.json"
    }
}