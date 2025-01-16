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

/**
 * Fonction composable qui gére l'affichage des recettes en favoris
 * Elle affiche les recettes favorites de l'utilisateur et permet d'interagir avec les recettes (ajouter/retirer des favoris, afficher les détails)
 *
 * @param recipeRepository le [RecipeRepository] qui fournit les données des recettes à afficher
 * @param navController le [NavController] utilisé pour naviguer vers l'écran de détails d'une recette
  */
@Composable
fun FavoritesScreen(
    recipeRepository: RecipeRepository,
    navController: NavController
) {
    // Crée un ViewModel pour gérer l'état des recettes
    val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(recipeRepository))
    val recipeState by viewModel.recipeState.collectAsState()
    val configuration = LocalConfiguration.current

    // Charger les recettes favorites dès le démarrage
    LaunchedEffect(Unit) {
        viewModel.getFavoriteRecipes()
    }
    // Déterminer l'orientation actuelle
    val isLandscape = remember(configuration.orientation) {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }


    FavoritesScreenContent(
        recipeState = recipeState,
        isLandscape = isLandscape,
        onHeartClick = { recipeId, isFavorite -> viewModel.toggleFavorite(recipeId, isFavorite) },
        onRecipeClick = { recipeId -> navController.navigate("recipeDetails/$recipeId") }
    )
}


/**
 * Fonction composable qui gère l'affichage du contenu de l'écran des recettes favorites.
 * Elle choisit la disposition des recettes en fonction de l'orientation de l'écran (liste ou mosaïque) puis les affiches.
 *
 * @param recipeState L'état des recettes (chargement, succès ou une erreur).
 * @param isLandscape Indicateur de l'orientation de l'écran (paysage ou portrait).
 * @param onHeartClick Callback qui est appelé lorsque l'utilisateur clique sur le bouton de favori (pour le supprimer)
 * @param onRecipeClick Callback qui est appelé lorsque l'utilisateur clique sur une recette pour y voir les détails.
 */
@Composable
fun FavoritesScreenContent(
    recipeState: RecipeState,
    isLandscape: Boolean,
    onHeartClick: (Int, Boolean) -> Unit,
    onRecipeClick: (Int) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when (recipeState) {
            // Affiche un indicateur de chargement si les recettes sont en cours de récupération
            is RecipeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            // Si les recettes sont récupérées avec succès, on affiche selon l'orientation de l'écran
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
            // Affichage d'un message d'erreur en cas de problème lors de la récupération des recettes
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

/**
 * Fonction composable qui affiche les recettes favorites sous forme de liste.
 * Chaque card contient une recette pour laquel nous pouvons les supprimer des favoris.
 *
 * @param recipes La liste des recettes à afficher.
 * @param onHeartClick Callback qui est appelé lorsque l'utilisateur clique sur le bouton de favori d'une recette.
 * @param onRecipeClick Callback qui est appelé lorsque l'utilisateur clique sur une recette pour voir les détails.
 */
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
                onClick = { onRecipeClick(recipe.id) },
                isLandscape = false
            )
        }
    }
}


/**
 * Fonction composable qui affiche les recettes favorites sous forme de mosaique.
 * (même chose que pour la représentation sous forme de liste)
 *
 * Chaque card contient une recette pour laquel nous pouvons les supprimer des favoris.
 *
 * @param recipes La liste des recettes à afficher.
 * @param onHeartClick Callback qui est appelé lorsque l'utilisateur clique sur le bouton de favori d'une recette.
 * @param onRecipeClick Callback qui est appelé lorsque l'utilisateur clique sur une recette pour voir les détails.
 */
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
                onClick = { onRecipeClick(recipe.id) },
                isLandscape = true
            )
        }
    }
}