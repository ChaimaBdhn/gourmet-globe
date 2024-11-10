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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(rvm: RecipeViewModel = viewModel()) {
    val recipes = rvm.recipes.value
    val isLoading = rvm.isLoading.value
    val error = rvm.error.value

    // Exemple de paramètres que tu veux passer
    val cuisine = "Italian"
    val diet = "vegetarian"
    val number = 10

    LaunchedEffect(Unit) {
        rvm.getRecipes(cuisine, diet, number)
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
