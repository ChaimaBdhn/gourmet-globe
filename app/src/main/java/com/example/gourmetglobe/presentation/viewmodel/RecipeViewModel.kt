package com.example.gourmetglobe.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetglobe.data.model.Recipe
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlin.math.log


class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // Utilisation de la Sealed Class RecipeState pour gérer l'état des recettes
    private val _recipeState = MutableStateFlow<RecipeState>(RecipeState.Loading)
    val recipeState: StateFlow<RecipeState> get() = _recipeState

    // Fonction de recherche de recettes
    fun searchRecipes(
        title: String?,
        cuisine: String?,
        diet: List<String>?,
        dishType: String?,
        intolerances: List<String>?,
        equipment: List<String>?,
        ingredients: List<String>?,
        minCalories: Int?,
        maxCalories: Int?,
        maxReadyTime: Int?
    ) {
        viewModelScope.launch {
            // Passer à l'état de chargement
            _recipeState.value = RecipeState.Loading

            try {
                recipeRepository.searchRecipes(
                    title, cuisine, diet, dishType, intolerances, equipment,
                    ingredients, minCalories, maxCalories, maxReadyTime
                ).catch { e ->
                    _recipeState.value = RecipeState.Error("Error fetching recipes: ${e.message}")
                }.collect { recipeEntities ->
                    // Convertir RecipeEntity en Recipe et émettre un état de succès
                    //_recipeState.value = RecipeState.Success(recipeEntities.map { it.toRecipe() })
                    _recipeState.value = RecipeState.Success(recipeEntities)
                    Log.d("test","je suis rentré")

                }
            } catch (e: Exception) {
                _recipeState.value = RecipeState.Error("Error fetching recipes: ${e.message}")
            }
        }
    }

    // Fonction pour récupérer les recettes favorites
    fun getFavoriteRecipes() {
        viewModelScope.launch {
            try {
                recipeRepository.getFavoriteRecipes()
                    .catch { e ->
                        _recipeState.value = RecipeState.Error("Error fetching favorite recipes: ${e.message}")
                    }.collect { recipeEntities ->
                        // Convertir RecipeEntity en Recipe et émettre un état de succès
                        //_recipeState.value = RecipeState.Success(recipeEntities.map { it.toRecipe() })
                        _recipeState.value = RecipeState.Success(recipeEntities)
                    }
            } catch (e: Exception) {
                _recipeState.value = RecipeState.Error("Error fetching favorite recipes: ${e.message}")
            }
        }
    }

    // Fonction pour changer le statut "favorite" d'une recette
    fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            recipeRepository.toggleFavorite(recipeId, isFavorite)
        }
    }

    // Fonction pour récupérer les détails d'une recette
    fun getRecipeDetails(id: Int) {
        viewModelScope.launch {
            try {
                val recipe = recipeRepository.getRecipeDetails(id)
                // Gérer le résultat de l'appel API ou de la base de données ici
            } catch (e: Exception) {
                _recipeState.value = RecipeState.Error("Error fetching recipe details: ${e.message}")
            }
        }
    }
}
