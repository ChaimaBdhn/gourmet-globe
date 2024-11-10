package com.example.gourmetglobe.data.api
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): RecipeResponse
}
