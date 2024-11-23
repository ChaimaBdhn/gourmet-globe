package com.example.gourmetglobe.domain.repository.repository

import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe


interface RecipeRepository {
    suspend fun getRecipesByFilters(
        title: String? = null,
        cuisine: String? = null,
        diet: String? = null,
        dishType: String? = null,
        intolerances: List<String>? = null,
        equipment: List<String>? = null,
        ingredients: List<String>? = null,
        minCalories: Int? = null,
        maxCalories: Int? = null,
        maxReadyTime: Int? = null,
        number: Int = 10
    ): List<RecipeEntity>

    suspend fun getRecipeDetails(id: Int): RecipeEntity?
}