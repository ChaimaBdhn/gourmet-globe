package com.example.gourmetglobe.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.components.RecipeCard
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory
@Composable
fun FavoritesScreen(recipeRepository: RecipeRepository, navController: NavController) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val recipeState by viewModel.recipeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getFavoriteRecipes() // Charger les recettes favorites dès le démarrage
    }

    when (val state = recipeState) {
        is RecipeState.Success -> {
            if (state.recipes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No favorite recipes yet!", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn {
                    items(state.recipes.size) { index ->
                        val recipe = state.recipes[index]
                        Log.d("favorites","${recipe}" )
                        RecipeCard(
                            recipe = recipe,
                            onHeartClick = { viewModel.toggleFavorite(recipe.id, !recipe.isFavorite) },
                            isFavorite = recipe.isFavorite,
                            onClick = {
                                // Naviguer vers la page de détails de la recette
                                navController.navigate("recipeDetails/${recipe.id}")
                            }
                        )
                    }
                }
            }
        }
        is RecipeState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is RecipeState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.message}")
            }
        }
    }
}
