package com.example.gourmetglobe.domain.repository.repository

import com.example.gourmetglobe.data.model.Recipe


interface RecipeRepository {
    suspend fun getRecipes(cuisine: String, diet: String, number: Int): List<Recipe>
}
