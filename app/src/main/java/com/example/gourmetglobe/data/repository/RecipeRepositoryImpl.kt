package com.example.gourmetglobe.data.repository

import com.example.gourmetglobe.data.api.RecipeApi
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val recipeDao: RecipeDAO // DAO pour gérer la base de données locale
) : RecipeRepository {

    override suspend fun getRecipesByFilters(
        cuisine: String?,
        diet: String?,
        dishType: String?,
        intolerances: List<String>?,
        equipment: List<String>?,
        minCalories: Float?,
        maxCalories: Float?,
        number: Int
    ): List<RecipeEntity> {
        return try {
            // Récupérer les recettes depuis l'API avec les paramètres de filtrage
            val response = api.searchRecipes(
                cuisine = cuisine,
                diet = diet,
                dishType = dishType,
                intolerances = intolerances.toString(),
                equipment = equipment.toString(),
                minCalories = minCalories,
                maxCalories = maxCalories,
                number = number,
                apiKey = "dfb061e309024285862277fff5f1028a"
            )

            val recipesFromApi = response.results

            // Convertir les résultats de l'API en RecipeEntity
            val recipeEntities = recipesFromApi.map { it.toEntity() }

            // Sauvegarder les données dans la base locale
            saveRecipesToLocalDatabase(recipeEntities)

            recipeEntities // renvoie la liste des recettes qui sont de type RecipeEntity

        } catch (e: Exception) {
            // En cas d'erreur, récupérer les données depuis la base locale
            withContext(Dispatchers.IO) {
                recipeDao.getRecipesByCuisine(cuisine)
            }
        }
    }

    private suspend fun saveRecipesToLocalDatabase(recipeEntities: List<RecipeEntity>) {
        withContext(Dispatchers.IO) {
            recipeDao.insertRecipes(recipeEntities)
        }
    }
}
