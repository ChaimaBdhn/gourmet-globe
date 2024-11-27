package com.example.gourmetglobe.domain.repository.repository

import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    // Recherche avec Flow (barre de recherche)
    fun searchRecipes(
        title: String? = null,
        cuisine: String? = null,
        diet: List<String>? = null,
        dishType: String? = null,
        intolerances: List<String>? = null,
        equipment: List<String>? = null,
        ingredients: List<String>? = null,
        minCalories: Int? = null,
        maxCalories: Int? = null,
        maxReadyTime: Int? = null
    ): Flow<List<RecipeEntity>>

    // Ajout ou suppression des favoris
    suspend fun toggleFavorite(recipeId: Int, isFavorite: Boolean)

    // Liste des favoris avec Flow
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    // Récupération d'une recette par son ID (hors ligne ou pas)
    suspend fun getRecipeDetails(id: Int): RecipeEntity?
}