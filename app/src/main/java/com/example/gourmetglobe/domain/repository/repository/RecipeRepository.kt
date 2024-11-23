package com.example.gourmetglobe.domain.repository.repository

import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe


interface RecipeRepository {
    suspend fun getRecipesByFilters(
        cuisine: String? = null,            // Type de cuisine (ex: Italian)
        diet: String? = null,              // Diète (ex: vegetarian, vegan)
        dishType: String? = null,          // Type de plat (ex: dessert, dinner)
        intolerances: List<String>? = null, // Liste des intolérances alimentaires
        equipment: List<String>? = null,   // Liste d'équipements nécessaires
        minCalories: Float? = null,        // Calories minimales (ex: 100)
        maxCalories: Float? = null,        // Calories maximales (ex: 500)
        number: Int = 10                   // Nombre de recettes à récupérer
    ): List<RecipeEntity>   // Retourne une liste d'entités RecipeEntity
}
