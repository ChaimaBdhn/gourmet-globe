package com.example.gourmetglobe.data.repository

import android.util.Log
import com.example.gourmetglobe.data.api.RecipeApi
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import kotlinx.coroutines.flow.*


/**
 * Implémentation du [RecipeRepository] pour gérer la logique d'accès aux données liées aux recettes.
 * Cette classe fournit des méthodes pour interagir avec l'API Spoonacular ainsi qu'avec la base de données locale
 * (Room) pour stocker et récupérer les données des recettes.
 *
 * Elle réalise également la mise à jour des favories des données dans la base de données locale.
 *
 * @param api L'instance de l'API Spoonacular pour récupérer les recettes depuis l'API externe.
 * @param recipeDao L'instance de l'interface DAO pour interagir avec la base de données locale (Room).
 */
class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val recipeDao: RecipeDAO

) : RecipeRepository {

    /**
     * Recherche des recettes en fonction des critères fournis (titre, cuisine, etc.).
     * Si l'API échoue, les résultats sont récupérés depuis la base de données locale.
     *
     * @param title Le titre de la recette (optionnel).
     * @param cuisine Le type de cuisine (optionnel).
     * @param diet Liste des régimes alimentaires (optionnel).
     * @param dishType Le type de plat (optionnel).
     * @param intolerances Liste des intolérances (optionnel).
     * @param equipment Liste des équipements nécessaires (optionnel).
     * @param ingredients Liste des ingrédients (optionnel).
     * @param minCalories Le nombre minimum de calories (optionnel).
     * @param maxCalories Le nombre maximum de calories (optionnel).
     * @param maxReadyTime Le temps de préparation maximum (optionnel).
     * @return Un [Flow] contenant la liste des recettes trouvées, soit depuis l'API soit depuis la base de données locale.
     */
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
                    apiKey = "b859eab2cceb402f980831a792ad4380"
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

    /**
     * Met à jour l'état des favoris pour une recette donnée dans la base de données locale.
     *
     * @param recipeId L'ID de la recette à mettre à jour.
     * @param isFavorite L'état des favoris (true ou false).
     */
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

    /**
     * Permet de récupérer les recettes favorites depuis la base de données locale.
     * @return Un [Flow] contenant la liste des recettes favorites.
     */
    override fun getFavoriteRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getFavoriteRecipes()
    }


    /**
     * Permet de récupérer toutes les recettes  depuis la base de données locale.
     * @return Un [Flow] contenant la liste de toute les recettes .
     */
    override fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    /**
     * Permet de récupérer le détails d'une recette  d'abord depuis l'api,
     * si une erreur survient, la recette est récupérée depuis la base de données locale.
     * @param id l\'id de la recette.
     * @return Le détails de la recette.
     */
    override suspend fun getRecipeDetails(id: Int): RecipeEntity {

        return try {

            val localRecipe = recipeDao.getRecipeByIdSync(id)
                val details = api.getRecipeDetails(id, "b859eab2cceb402f980831a792ad4380").toEntity()
                .mergeFavorites(localRecipe)

            recipeDao.updateRecipe(details)

            details
        } catch (e: Exception) {
            recipeDao.getRecipeByIdSync(id)
        }
    }
}
