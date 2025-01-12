package com.example.gourmetglobe.presentation.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.components.RecipeCard
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory

@Composable
fun FavoritesScreen(
    recipeRepository: RecipeRepository,
    navController: NavController
) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val recipeState by viewModel.recipeState.collectAsState()

    // Charger les recettes favorites dès le démarrage
    LaunchedEffect(Unit) {
        viewModel.getFavoriteRecipes()
    }

    FavoritesScreenContent(
        recipeState = recipeState,
        onHeartClick = { recipeId, isFavorite -> viewModel.toggleFavorite(recipeId, isFavorite) },
        onRecipeClick = { recipeId -> navController.navigate("recipeDetails/$recipeId") }
    )
}

@Composable
fun FavoritesScreenContent(
    recipeState: RecipeState,
    onHeartClick: (Int, Boolean) -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    // Détection de l'orientation
    val isLandscape = isLandscape()

    Box(modifier = Modifier.fillMaxSize()) {
        when (recipeState) {
            is RecipeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is RecipeState.Success -> {
                if (recipeState.recipes.isEmpty()) {
                    Text(
                        text = "No favorite recipes yet!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    if (isLandscape) {
                        MosaicRecipes(
                            recipes = recipeState.recipes,
                            onHeartClick = onHeartClick,
                            onRecipeClick = onRecipeClick
                        )
                    } else {
                        ListRecipes(
                            recipes = recipeState.recipes,
                            onHeartClick = onHeartClick,
                            onRecipeClick = onRecipeClick
                        )
                    }
                }
            }
            is RecipeState.Error -> {
                Text(
                    text = "Error: ${recipeState.message}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ListRecipes(
    recipes: List<RecipeEntity>,
    onHeartClick: (Int, Boolean) -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipes.size) { index ->
            val recipe = recipes[index]
            RecipeCard(
                recipe = recipe,
                onHeartClick = { onHeartClick(recipe.id, !recipe.isFavorite) },
                isFavorite = recipe.isFavorite,
                onClick = { onRecipeClick(recipe.id) }
            )
        }
    }
}

@Composable
fun MosaicRecipes(
    recipes: List<RecipeEntity>,
    onHeartClick: (Int, Boolean) -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipes.size) { index ->
            val recipe = recipes[index]
            RecipeCard(
                recipe = recipe,
                onHeartClick = { onHeartClick(recipe.id, !recipe.isFavorite) },
                isFavorite = recipe.isFavorite,
                onClick = { onRecipeClick(recipe.id) }
            )
        }
    }
}

@Composable
fun isLandscape(): Boolean {
    val orientation = LocalConfiguration.current.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}