package com.example.gourmetglobe.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    // Créer une instance de ViewModel avec RecipeViewModelFactory
    val viewModel: RecipeViewModel = viewModel(
        factory = RecipeViewModelFactory(recipeRepository)
    )

    val recipes = viewModel.recipes.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    // Exemple de paramètres que tu veux passer
    val cuisine = "Italian"
    val diet = "vegetarian"
    val number = 10

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
                RecipeList(recipes = recipes)
            }
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes.size) { recipe ->
            RecipeCard(recipe = recipes[recipe])
        }
    }
}
