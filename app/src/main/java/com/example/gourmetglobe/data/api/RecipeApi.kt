package com.example.gourmetglobe.data.api
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/complexSearch")
    fun searchRecipes(
        @Query("query") title: String? = null, // Titre de la recette
        @Query("includeIngredients") ingredients: String? = null, // Ingrédients
        @Query("cuisine") cuisine: String? = null, // Type de cuisine
        @Query("diet") diet: String? = null, // Type de plat
        @Query("type") dishType: String? = null, // Type de plat
        @Query("equipment") equipment: String? = null, // Ustensiles
        @Query("maxCalories") maxCalories: Int? = null, // Calories max
        @Query("minCalories") minCalories: Int? = null, // Calories min
        @Query("maxReadyTime") maxReadyTime: Int? = null, // Temps de préparation max
        @Query("intolerances") intolerances: String? = null, // Allergènes
        @Query("number") number: Int = 10, // Nombre de recettes
        @Query("apiKey") apiKey: String    // Clé API
    ): RecipeResponse


    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,           // ID de la recette
        @Query("apiKey") apiKey: String // Clé API
    ): RecipeDetailsResponse

}
