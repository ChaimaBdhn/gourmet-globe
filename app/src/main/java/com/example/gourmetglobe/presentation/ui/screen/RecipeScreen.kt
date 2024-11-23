package com.example.gourmetglobe.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gourmetglobe.MyApplication
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.database.RecipeDatabase
import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.data.model.Recipe
import com.example.gourmetglobe.data.model.RecipeService.api
import com.example.gourmetglobe.data.repository.RecipeRepositoryImpl
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory
import com.example.gourmetglobe.presentation.ui.state.RecipeState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen() {
    // Accéder au repository depuis MyApplication
    val app = LocalContext.current.applicationContext as MyApplication
    val recipeRepository = app.recipeRepository

    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))

    // Observer l'état des recettes depuis le ViewModel
    val recipeState by viewModel.recipeState.collectAsState()
    val isLoading = recipeState is RecipeState.Loading
    val error = (recipeState as? RecipeState.Error)?.message
    val recipes = (recipeState as? RecipeState.Success)?.recipes ?: emptyList()

    // Paramètres pour tester
    val cuisine = "Italian"
    val diet = "vegetarian"
    val number = 10

    // Charger les recettes dès le début
    LaunchedEffect(Unit) {
        viewModel.searchRecipes(
            title = null,
            cuisine = cuisine,
            diet = listOf(diet),
            dishType = null,
            intolerances = null,
            equipment = null,
            ingredients = null,
            minCalories = null,
            maxCalories = null,
            maxReadyTime = null
        )
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
                // Afficher la liste des recettes
                RecipeList(
                    recipes = recipes,
                    onHeartClick = { recipe ->
                        viewModel.toggleFavorite(recipe.id, !recipe.isFavorite)
                    }
                )
            }
        }
    }
}

@Composable
fun RecipeList(
    recipes: List<RecipeEntity>,
    onHeartClick: (RecipeEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipes.size) { index ->
            val recipe = recipes[index]
            RecipeCard(
                recipe = recipe,
                onHeartClick = onHeartClick,
                isFavorite = recipe.isFavorite // Vérification des favoris
            )
        }
    }
}
