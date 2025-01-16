package com.example.gourmetglobe.presentation.navigation

import  com.example.gourmetglobe.presentation.ui.screens.RecipeDetailScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.screens.FavoritesScreen
import com.example.gourmetglobe.presentation.ui.screens.HomeScreen
import com.example.gourmetglobe.presentation.ui.screens.SearchScreen


/**
 * Fonction composable qui gére la navigation de l'application.
 * Elle utilise un [NavController] pour naviguer entre différentes pages (Accueil, recherche,favorie,détails d'une recette).
 * La navigation est définie à travers un [NavHost] qui gère la navigation en fonction des routes.
 *
 * @param recipeRepository Le [RecipeRepository] qui fournit les données des recettes pour les différentes pages.
 */
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