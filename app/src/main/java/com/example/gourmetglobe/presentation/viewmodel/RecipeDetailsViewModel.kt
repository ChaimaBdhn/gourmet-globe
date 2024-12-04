package com.example.gourmetglobe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.state.RecipeDetailsState

class RecipeDetailsViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _recipeDetailsState = MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)
    val recipeDetailsState: StateFlow<RecipeDetailsState> = _recipeDetailsState

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
