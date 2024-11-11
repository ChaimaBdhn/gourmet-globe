package com.example.gourmetglobe.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetglobe.data.model.Recipe
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = mutableStateOf<List<Recipe>>(listOf())
    val recipes: State<List<Recipe>> = _recipes

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error



    // Méthode pour récupérer les recettes en fonction des paramètres
    fun getRecipes(cuisine: String, diet: String, number: Int) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                // Appel à la méthode du repository avec les paramètres dynamiques
                _recipes.value = repository.getRecipes(cuisine, diet, number)
                Log.d("RecipeViewModel", "Fetched recipes: ${_recipes.value.size}")
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des recettes"
                Log.e("RecipeViewModel", "Error fetching recipes", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
