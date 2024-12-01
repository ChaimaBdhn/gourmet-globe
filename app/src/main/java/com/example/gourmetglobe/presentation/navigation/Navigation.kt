package com.example.gourmetglobe.presentation.navigation

import RecipeDetailScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.screens.FavoritesScreen
import com.example.gourmetglobe.presentation.ui.screens.HomeScreen
import com.example.gourmetglobe.presentation.ui.screens.SearchScreen
import com.example.gourmetglobe.presentation.ui.components.TopBar


@Composable
fun Navigation(recipeRepository: RecipeRepository) {
    val navController = rememberNavController()
    // Stocker l'état de la recette
    var recipeState by remember { mutableStateOf<RecipeEntity?>(null) }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home", // Page d'accueil par défaut
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    recipeRepository = recipeRepository,
                    navController = navController
                )
            }
            composable("search") {
                SearchScreen(
                    recipeRepository = recipeRepository,
                    navController = navController
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    recipeRepository = recipeRepository,
                    navController = navController
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    recipeRepository = recipeRepository,
                    navController = navController
                )
            }
            composable("recipeDetails/{recipeId}") { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")
                if (recipeId != null) {
                    RecipeDetailScreen(recipeId = recipeId.toInt(), recipeRepository = recipeRepository  ,navController = navController)
                }
            }
        }
    }
}
