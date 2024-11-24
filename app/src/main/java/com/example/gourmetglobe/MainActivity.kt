package com.example.gourmetglobe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gourmetglobe.presentation.ui.screen.RecipeScreen
import com.example.gourmetglobe.ui.theme.GourmetGlobeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "je suis dans la mainActivité AVANT")

        super.onCreate(savedInstanceState)
//        Log.d("coucou", "je suis dans la mainActivité AVANT")
//
//        // Récupérer l'instance de MyApplication pour accéder aux dépendances
//        val app = application as MyApplication
//        Log.d("test", "je suis dans la mainActivité AVANT recipe")
//
//        // Accéder au repository directement via l'application
//        val recipeRepository = app.recipeRepository
//        Log.d("test", "je suis dans la mainActivité APRES recipe")

        setContent {
            Log.d("MainActivity", "App started successfully")

            GourmetGlobeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Appeler la fonction RecipeScreen en lui passant le repository
                    //RecipeScreen(recipeRepository = recipeRepository) // RecipeScreen a maintenant accès au repository
                }
            }
        }
    }
}
