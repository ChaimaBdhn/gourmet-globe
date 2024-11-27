package com.example.gourmetglobe.presentation.ui.state

import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe

//Sealed Class pour représenter les différents états des recettes
sealed class RecipeState {
    object Loading : RecipeState() // État de chargement
    data class Success(val recipes: List<RecipeEntity>) : RecipeState() // État de succès avec les données des recettes
    data class Error(val message: String) : RecipeState() // État d'erreur avec le message d'erreur
}