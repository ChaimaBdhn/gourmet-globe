package com.example.gourmetglobe.data.model
import com.example.gourmetglobe.data.api.RecipeApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RecipeService {
    private const val BASE_URL = "https://api.spoonacular.com/"

    val api: RecipeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }
}
