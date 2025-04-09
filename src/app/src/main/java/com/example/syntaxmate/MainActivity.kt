package com.example.syntaxmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.syntaxmate.data.repository.LanguageRepository
import com.example.syntaxmate.ui.navigation.AppNavigation
import com.example.syntaxmate.ui.theme.SyntaxMateTheme
import com.example.syntaxmate.viewmodel.LanguageViewModel

class MainActivity : ComponentActivity() {

    companion object {
        private const val LOG_TAG = "MainLog"
    }

    private val languageRepository: LanguageRepository by lazy {
        LanguageRepository(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = provideLanguageViewModelFactory(languageRepository)
        val languageViewModel: LanguageViewModel =
            ViewModelProvider(this, factory).get(LanguageViewModel::class.java)


        enableEdgeToEdge()
        setContent {
            SyntaxMateTheme { // Tema configurado em .ui.theme
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.app_name)) }
                        )
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(
                            navController = navController,
                            languageViewModel = languageViewModel
                        )
                    }
                }
            }
        }


    }

    private fun provideLanguageViewModelFactory(repository: LanguageRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return LanguageViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SyntaxMateTheme {

        }
    }
}