package com.example.gourmetglobe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gourmetglobe.presentation.ui.screen.RecipeScreen
import com.example.gourmetglobe.ui.theme.GourmetGlobeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupérer l'instance de MyApplication pour accéder aux dépendances
        val app = application as MyApplication

        // Accéder au repository directement via l'application
        val recipeRepository = app.recipeRepository

        setContent {
            Log.d("MainActivity", "App started successfully")
            GourmetGlobeTheme {
                // Utiliser LocalContext.current pour obtenir le contexte
                val context = LocalContext.current

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecipeScreen() // RecipeScreen n'a plus besoin de recevoir un contexte ou repository
                }
            }
        }
    }
}
