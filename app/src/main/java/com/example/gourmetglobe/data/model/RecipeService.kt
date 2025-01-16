package com.example.gourmetglobe.data.model
import com.example.gourmetglobe.data.api.RecipeApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Service de gestion de la communication avec l'API de recettes.
 * Cette classe permet de configurer et d'initialiser le client Retrofit qui se connecte à l'API Spoonacular
 * pour récupérer les données des recettes, tout en utilisant Moshi pour la conversion des données JSON.
 *
 * Comme vue en td, on utilise le singleton pour créer et partager une instance de l'API, ce qui garantit qu'une seule
 * instance de Retrofit est utilisée dans l'application pour toutes les requêtes.
 */
object RecipeService {
    private const val BASE_URL = "https://api.spoonacular.com/"

    val api: RecipeApi by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RecipeApi::class.java)
    }
}
