package com.example.gourmetglobe.presentation.ui.screens

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
import com.example.gourmetglobe.presentation.ui.components.TopBar
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory

@Composable
fun HomeScreen(recipeRepository: RecipeRepository, navController: NavController) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val recipeState by viewModel.recipeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllRecipes() // Méthode pour récupérer des recettes aléatoires
    }
    Scaffold(
        topBar = {
            // Intégrer le TopAppBar avec le NavController
            TopBar(navController = navController, title = "Home")
        },
        content = { paddingValues ->
            when (val state = recipeState) {
                is RecipeState.Success -> {
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        items(state.recipes.size) { index ->
                            val recipe = state.recipes[index]
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
    )
}
