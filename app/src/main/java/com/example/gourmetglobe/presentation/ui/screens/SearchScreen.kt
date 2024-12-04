package com.example.gourmetglobe.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.components.RecipeCard
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory

@Composable
fun SearchScreen(recipeRepository: RecipeRepository, navController: NavController) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val query = remember { mutableStateOf("") }
    val recipeState by viewModel.recipeState.collectAsState()
    val isSearching = query.value.isNotEmpty()

    // Recherche en temps réel
    LaunchedEffect(query.value) {
        if (query.value.isNotEmpty()) {
            viewModel.searchRecipes(
                title = query.value.ifBlank { null },
                cuisine = null,
                diet = null,
                dishType = null,
                intolerances = null,
                equipment = null,
                ingredients = null,
                minCalories = null,
                maxCalories = null,
                maxReadyTime = null
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Champ de recherche
        SearchField(query = query)

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des résultats ou chargement
        when (val state = recipeState) {
            is RecipeState.Loading -> {
                if( isSearching){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Commencez la recherche !")
                    }
                }
            }
            is RecipeState.Success -> {
                if (state.recipes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Aucune recette trouvée.")
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(state.recipes, key = { it.id })  { recipe  ->
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
            is RecipeState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Erreur: ${state.message}")
                }
            }
        }
    }
}

@Composable
fun SearchField(query: MutableState<String>) {
    OutlinedTextField(
        value = query.value,
        onValueChange = { query.value = it },
        label = { Text("Rechercher une recette") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Recherche à la soumission du clavier
            }
        )
    )
}
