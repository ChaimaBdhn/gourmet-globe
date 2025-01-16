package com.example.gourmetglobe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.state.RecipeDetailsState


/**
 * ViewModel pour gérer l'état et les actions liées aux détails d'une recette.
 * @property recipeRepository Le référentiel utilisé pour récupérer les données des recettes.
 */
class RecipeDetailsViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    // État interne mutable représentant l'état des détails de la recette.
    private val _recipeDetailsState = MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)

    // Flux d'état public exposant l'état des détails de la recette.
    val recipeDetailsState: StateFlow<RecipeDetailsState> = _recipeDetailsState


    /**
     * Récupère les détails d'une recette spécifique en fonction de son identifiant.
     *
     * @param recipeId L'identifiant unique de la recette à récupérer.
     */
    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipe = recipeRepository.getRecipeDetails(recipeId)
                _recipeDetailsState.value = RecipeDetailsState.Success(recipe)
            } catch (e: Exception) {
                _recipeDetailsState.value = RecipeDetailsState.Error("Error fetching recipe details: ${e.message}")
            }
        }
    }
}