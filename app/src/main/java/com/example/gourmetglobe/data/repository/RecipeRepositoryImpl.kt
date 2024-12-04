package com.example.gourmetglobe.data.repository

import android.util.Log
import com.example.gourmetglobe.data.api.RecipeApi
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.flow.*

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val recipeDao: RecipeDAO

) : RecipeRepository {

    override fun searchRecipes(
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
    ): Flow<List<RecipeEntity>> {

        return flow{
            try {
                // Étape 1 : Appeler l'API
                val apiResults = api.searchRecipes(
                    title = title,
                    cuisine = cuisine,
                    diet = diet?.joinToString(","),
                    dishType = dishType,
                    intolerances = intolerances?.joinToString(","),
                    equipment = equipment?.joinToString(","),
                    ingredients = ingredients?.joinToString(","),
                    minCalories = minCalories,
                    maxCalories = maxCalories,
                    maxReadyTime = maxReadyTime,
                    apiKey = "dfb061e309024285862277fff5f1028a"
                ).results
                Log.d("SearchRecipes", "${apiResults}")

                // Convertir les résultats API en RecipeEntity
                val apiRecipeEntities = apiResults.map { it.toEntity() }


                // Sauvegarder les résultats de l'API dans la base locale
                Log.d("SearchRecipes", "${apiRecipeEntities}")

                recipeDao.insertRecipes(apiRecipeEntities)

                Log.d("SearchRecipes", "Je suis après l'insertion dans Room")


                // Émettre les résultats de l'API

                emit(apiRecipeEntities)
            } catch (e: Exception) {
                // Étape 3 : Récupérer les résultats depuis la base locale en cas d'erreur
                val localResults = recipeDao.getRecipesByFilters(
                    title = title,
                    cuisine = cuisine,
                    diet = diet?.joinToString(","),
                    dishType = dishType,
                    intolerances = intolerances?.joinToString(","),
                    equipment = equipment?.joinToString(","),
                    ingredients = ingredients?.joinToString(","),
                    minCalories = minCalories,
                    maxCalories = maxCalories,
                    maxReadyTime = maxReadyTime
                ).first() // on met first pour recuperer les valeurs qui arrivent dans le flux ("une part une le temps qu'elles arrivent")
                emit(localResults)
            }
        }
    }


    override suspend fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        val recipe = recipeDao.getRecipeByIdSync(recipeId) // Requête synchrone
        recipe.isFavorite = isFavorite
        try {
            Log.d("test", "${recipe}")
            recipeDao.updateRecipe(recipe)
        } catch (e:Exception) {
            Log.e("test", e.toString())
        }
    }

    override fun getFavoriteRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getFavoriteRecipes()
    }



    override fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    override suspend fun getRecipeDetails(id: Int): RecipeEntity {

        return try {

            val localRecipe = recipeDao.getRecipeByIdSync(id)
            val details = api.getRecipeDetails(id, "dfb061e309024285862277fff5f1028a").toEntity()
                .mergeFavorites(localRecipe)

            recipeDao.updateRecipe(details)

            details
        } catch (e: Exception) {
            recipeDao.getRecipeByIdSync(id)
        }
    }
}
