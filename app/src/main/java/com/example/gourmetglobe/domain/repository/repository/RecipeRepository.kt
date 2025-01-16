package com.example.gourmetglobe.domain.repository.repository

import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Interface représentant avec laquel on pourra faire les requêtes sur notre base de données locale (room) pour les recettes.
 * Fournit des méthodes pour interagir avec la base de donnée
 * telles que la recherche, la gestion des favoris et l'accès aux détails des recettes.
 */
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
    suspend fun getRecipeDetails(id: Int): RecipeEntity

    // Récupération de toute les recettes disponible dans la base avec Flow
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}
