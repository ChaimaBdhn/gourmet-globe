package com.example.gourmetglobe.data.repository

import com.example.gourmetglobe.data.api.RecipeApi
import com.example.gourmetglobe.data.model.Recipe
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val api: RecipeApi
) : RecipeRepository {

    override suspend fun getRecipes(cuisine: String, diet: String, number: Int): List<Recipe> {
        val response = api.getRecipes(cuisine, diet, number, "dfb061e309024285862277fff5f1028a")
        return response.results
    }
}
