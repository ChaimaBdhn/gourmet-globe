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
        title: String?,
        cuisine: String?,
        diet: String?,
        dishType: String?,
        intolerances: List<String>?,
        equipment: List<String>?,
        ingredients: List<String>?,
        minCalories: Int?,
        maxCalories: Int?,
        maxReadyTime: Int?,
        number: Int
    ): List<RecipeEntity> {
        return try {
            // Récupérer les recettes depuis l'API avec les paramètres de filtrage
            val response = api.searchRecipes(
                title = title,
                cuisine = cuisine,
                diet = diet,
                dishType = dishType,
                intolerances = intolerances?.joinToString(","),
                equipment = equipment?.joinToString(","),
                ingredients = ingredients?.joinToString(","),
                minCalories = minCalories,
                maxCalories = maxCalories,
                maxReadyTime = maxReadyTime,
                number = number,
                apiKey = "dfb061e309024285862277fff5f1028a"
            )

            val recipesFromApi = response.results

            // Convertir les résultats de l'API en RecipeEntity
            val recipeEntities = recipesFromApi.map { it.toEntity() }

            // Sauvegarder les données dans la base locale
            saveRecipesToLocalDatabase(recipeEntities)

            recipeEntities // Renvoie la liste des recettes qui sont de type RecipeEntity

        } catch (e: Exception) {
            // En cas d'erreur, récupérer les données depuis la base locale
            withContext(Dispatchers.IO) {
                recipeDao.getRecipesByFilters(
                    title = title,
                    cuisine = cuisine,
                    diet = diet,
                    dishType = dishType,
                    intolerances = intolerances,
                    equipment = equipment,
                    ingredients = ingredients,
                    minCalories = minCalories,
                    maxCalories = maxCalories,
                    maxReadyTime = maxReadyTime
                )
            }
        }
    }

    override suspend fun getRecipeDetails(id: Int): RecipeEntity? {
        return try {
            // Récupérer les détails d'une recette via l'API
            val recipeDetails = api.getRecipeDetails(
                id = id,
                apiKey = "dfb061e309024285862277fff5f1028a"
            )

            // Convertir les détails de la recette en RecipeEntity
            val recipeEntity = recipeDetails.toEntity()

            // Sauvegarder ou mettre à jour la recette dans la base locale
            saveRecipeToLocalDatabase(recipeEntity)

            recipeEntity // Retourner la recette récupérée et sauvegardée
        } catch (e: Exception) {
            // En cas d'erreur, récupérer depuis la base locale
            withContext(Dispatchers.IO) {
                recipeDao.getRecipeById(id)
            }
        }
    }

    private suspend fun saveRecipesToLocalDatabase(recipeEntities: List<RecipeEntity>) {
        withContext(Dispatchers.IO) {
            recipeDao.insertRecipes(recipeEntities)
        }
    }

    private suspend fun saveRecipeToLocalDatabase(recipeEntity: RecipeEntity) {
        withContext(Dispatchers.IO) {
            recipeDao.insertRecipe(recipeEntity)
        }
    }
}
