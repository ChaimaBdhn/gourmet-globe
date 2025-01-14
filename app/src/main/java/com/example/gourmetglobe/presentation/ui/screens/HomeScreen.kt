package com.example.gourmetglobe.presentation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.components.RecipeCard
import com.example.gourmetglobe.presentation.ui.components.TopBar
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory

@Composable
fun HomeScreen(recipeRepository: RecipeRepository, navController: NavController) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val recipeState by viewModel.recipeState.collectAsState()
    val configuration = LocalConfiguration.current

    // Déterminer l'orientation actuelle
    val isLandscape = remember(configuration.orientation) {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    // Charger les recettes au démarrage
    LaunchedEffect(Unit) {
        viewModel.getAllRecipes()
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Home")
        },
        content = { paddingValues ->
            HomeScreenContent(
                recipeState = recipeState,
                isLandscape = isLandscape,
                onHeartClick = { recipeId, isFavorite -> viewModel.toggleFavorite(recipeId, isFavorite) },
                onRecipeClick = { recipeId -> navController.navigate("recipeDetails/$recipeId") },
                paddingValues = paddingValues
            )
        }
    )
}

@Composable
fun HomeScreenContent(
    recipeState: RecipeState,
    isLandscape: Boolean,
    onHeartClick: (Int, Boolean) -> Unit,
    onRecipeClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    when (recipeState) {
        is RecipeState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is RecipeState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${(recipeState as RecipeState.Error).message}")
            }
        }
        is RecipeState.Success -> {
            if ((recipeState as RecipeState.Success).recipes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No recipes available!", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                // Afficher les recettes en mosaïque ou en liste selon l'orientation
                if (isLandscape) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        contentPadding = paddingValues
                    ) {
                        items(recipeState.recipes.size) { index ->
                            val recipe = recipeState.recipes[index]
                            RecipeCard(
                                recipe = recipe,
                                onHeartClick =  { onHeartClick(recipe.id, !recipe.isFavorite) },
                                isFavorite = recipe.isFavorite,
                                onClick = { onRecipeClick(recipe.id) }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(recipeState.recipes.size) { index ->
                            val recipe = recipeState.recipes[index]
                            RecipeCard(
                                recipe = recipe,
                                onHeartClick = { onHeartClick(recipe.id, !recipe.isFavorite) },
                                isFavorite = recipe.isFavorite,
                                onClick = { onRecipeClick(recipe.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
