package com.example.gourmetglobe.presentation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.components.RecipeCard
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModelFactory


/**
 * Composable pour l'écran de recherche
 *
 * @param recipeRepository contenant les données des recettes
 * @param navController Le contrôleur de navigation pour gérer les transitions entre écrans
 */

@Composable
fun SearchScreen(recipeRepository: RecipeRepository, navController: NavController) {
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val query = rememberSaveable { mutableStateOf("") }
    val recipeState by viewModel.recipeState.collectAsState()
    val isSearching = query.value.isNotEmpty()

    val configuration = LocalConfiguration.current
    val isLandscape = remember(configuration.orientation) {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
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
                        Text(text = "start your search !")
                    }
                }
            }
            is RecipeState.Success -> {
                if (state.recipes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "There are no results for this search.")
                    }
                } else {
                    if(isLandscape){
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.recipes.size) { index ->
                                val recipe = state.recipes[index]
                                RecipeCard(
                                    recipe = recipe,
                                    onHeartClick =  { viewModel.toggleFavorite(recipe.id, !recipe.isFavorite) },
                                    isFavorite = recipe.isFavorite,
                                    onClick = {
                                        // Naviguer vers la page de détails de la recette
                                        navController.navigate("recipeDetails/${recipe.id}")
                                    },
                                    isLandscape = true
                                )
                            }
                        }
                    } else{
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
                                        },
                                        isLandscape = false
                                    )
                                }
                            }
                        }
                    }
                }

            is RecipeState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.message}")
                }
            }
        }
    }
}
/**
 * Champ de recherche utilisé pour entrer une requête utilisateur.
 * la recherche doit se faire selon le nom d'un plat / d'une recette
 * @param query L'état mutable contenant la requête de recherche.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(query: MutableState<String>) {
    OutlinedTextField(
        value = query.value,
        onValueChange = { query.value = it },
        label = { Text("Find a recipe") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Recherche à la soumission du clavier
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colorScheme.secondary, // Couleur de bordure lorsqu'il est focus
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary, // Couleur de bordure au repos
        cursorColor = MaterialTheme.colorScheme.secondary, // Couleur du curseur
            focusedTextColor = MaterialTheme.colorScheme.secondary
        )
    )
}

