package com.example.gourmetglobe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.launch
import com.example.gourmetglobe.presentation.ui.state.RecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart


/**
 * ViewModel pour gérer la logique métier liée aux recettes.
 *
 * @property recipeRepository "la room" utilisé pour interagir avec les données des recettes.
 */
class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // Utilisation de la Sealed Class RecipeState pour gérer l'état des recettes
    private val _recipeState = MutableStateFlow<RecipeState>(RecipeState.Loading)
    val recipeState: StateFlow<RecipeState> get() = _recipeState



    /**
     * Recherche des recettes en fonction des critères spécifiés.
     * (Seul le titre est pris en compte dans cette version)
     * @param title Titre de la recette.
     * @param cuisine Type de cuisine (italienne, française, etc.).
     * @param diet Régimes alimentaires (végétalien, végétarien, etc.).
     * @param dishType Type de plat (entrée, dessert, etc.).
     * @param intolerances Intolérances alimentaires (gluten, lactose, etc.).
     * @param equipment Équipements nécessaires pour la recette.
     * @param ingredients Ingrédients requis pour la recette.
     * @param minCalories Nombre minimum de calories.
     * @param maxCalories Nombre maximum de calories.
     * @param maxReadyTime Temps de préparation maximal.
     */
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
                )
                    .onStart {
                        Log.d("SearchRecipes", "Recherche commencée avec les critères : titre=$title, cuisine=$cuisine, diet=$diet, dishType=$dishType, intolerances=$intolerances, equipment=$equipment, ingredients=$ingredients, minCalories=$minCalories, maxCalories=$maxCalories, maxReadyTime=$maxReadyTime")
                    }

                    .catch { e ->

                        Log.e("SearchRecipes", "Erreur lors de la recherche : ${e.message}", e)
                        _recipeState.value = RecipeState.Error("Erreur lors de la recherche : ${e.message}")
                    }

                    .collect { recipeEntities ->
                        if (recipeEntities.isEmpty()) {

                            Log.w("SearchRecipes", "Aucune recette trouvée avec les critères fournis.")
                            _recipeState.value = RecipeState.Error("Aucune recette trouvée.")

                        } else {
                            Log.d("SearchRecipes", "Recettes récupérées avec succès : ${recipeEntities.size} recettes.")
                            // Convertir les entités récupérées en modèles métier (si nécessaire)
                            _recipeState.value = RecipeState.Success(recipeEntities)
                        }
                    }
            } catch (e: Exception) {
                // Gérer les exceptions générales
                Log.e("SearchRecipes", "Exception inattendue : ${e.message}", e)
                _recipeState.value = RecipeState.Error("Erreur inattendue : ${e.message}")
            }
        }
    }

    /**
     *  Fonction pour récupérer les recettes favorites
     */
    fun getFavoriteRecipes() {
        viewModelScope.launch {
            try {
                recipeRepository.getFavoriteRecipes()
                    .catch { e ->
                        _recipeState.value = RecipeState.Error("Error fetching favorite recipes: ${e.message}")
                    }.collect { recipeEntities ->
                        _recipeState.value = RecipeState.Success(recipeEntities)
                    }
            } catch (e: Exception) {
                _recipeState.value = RecipeState.Error("Error fetching favorite recipes: ${e.message}")
            }
        }
    }

    /**
     * Met à jour l'état d'une recette favorite (ajout ou suppression).
     *
     * @param recipeId Identifiant de la recette.
     * @param isFavorite Nouvel état de favori.
     */
    fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                // Met à jour dans la base de données
                recipeRepository.toggleFavorite(recipeId, isFavorite)

                // Met à jour dans l'état local (Immutable List Copy)
                val currentState = _recipeState.value
                if (currentState is RecipeState.Success) {
                    Log.d("toggleFavorite", "Je suis dedans")

                    val updatedRecipes = currentState.recipes.map { recipe ->
                        if (recipe.id == recipeId) recipe.copy(isFavorite = isFavorite) else recipe
                    }
                    _recipeState.value = RecipeState.Success(updatedRecipes)
                }
            } catch (e: Exception) {
                Log.e("toggleFavorite", "Erreur lors de la mise à jour du favori : ${e.message}", e)
            }
        }
    }

    /**
     * Rafraîchit toutes les recettes disponibles.
     */
    private fun refreshRecipes() {
        viewModelScope.launch {
            recipeRepository.getAllRecipes()
                .catch { e -> _recipeState.value = RecipeState.Error("Error loading recipes: ${e.message}") }
                .collect { recipes ->
                    _recipeState.value = RecipeState.Success(recipes)
                }
        }
    }

    /**
     * Récupère toutes les recettes disponibles.
     */
    fun getAllRecipes(){
        refreshRecipes()
    }

}