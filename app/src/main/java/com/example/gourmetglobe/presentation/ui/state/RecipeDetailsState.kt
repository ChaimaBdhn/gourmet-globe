package com.example.gourmetglobe.presentation.ui.state

import com.example.gourmetglobe.data.local.entities.RecipeEntity

sealed class RecipeDetailsState {
    object Loading : RecipeDetailsState()
    data class Success(val recipe: RecipeEntity) : RecipeDetailsState()
    data class Error(val message: String) : RecipeDetailsState()
}
