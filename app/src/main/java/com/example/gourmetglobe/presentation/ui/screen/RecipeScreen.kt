package com.example.gourmetglobe.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.data.model.Recipe
import com.example.gourmetglobe.data.model.RecipeService.api
import com.example.gourmetglobe.data.repository.RecipeRepositoryImpl
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen() {
    val recipeRepository: RecipeRepository = RecipeRepositoryImpl(api)

    val viewModel: RecipeViewModel = viewModel(
        factory = RecipeViewModelFactory(recipeRepository)
    )

    val recipes = viewModel.recipes.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    // Fixation des paramètres pour tester
    val cuisine = "Italian"
    val diet = "vegetarian"
    val number = 10

    // État local pour gérer les favoris
    var favoriteRecipes by remember { mutableStateOf(setOf<Int>()) } // Set d'IDs favoris

    // Gestion des clics sur le bouton cœur
    val onHeartClick: (Recipe) -> Unit = { recipe ->
        favoriteRecipes = if (favoriteRecipes.contains(recipe.id)) {
            favoriteRecipes - recipe.id // Supprimer des favoris
        } else {
            favoriteRecipes + recipe.id // Ajouter aux favoris
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getRecipes(cuisine, diet, number)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("GourmetGlobe Recipes") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            } else {
                RecipeList(
                    recipes = recipes,
                    favoriteRecipes = favoriteRecipes,
                    onHeartClick = onHeartClick
                )
            }
        }
    }
}

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    favoriteRecipes: Set<Int>,
    onHeartClick: (Recipe) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes.size) { index ->
            val recipe = recipes[index]
            RecipeCard(
                recipe = recipe,
                onHeartClick = onHeartClick,
                isFavorite = favoriteRecipes.contains(recipe.id)
            )
        }
    }
}

