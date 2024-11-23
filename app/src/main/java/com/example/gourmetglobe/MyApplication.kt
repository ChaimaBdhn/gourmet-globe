package com.example.gourmetglobe

import android.app.Application
import com.example.gourmetglobe.data.local.database.RecipeDatabase
import com.example.gourmetglobe.data.model.RecipeService
import com.example.gourmetglobe.data.repository.RecipeRepositoryImpl

class MyApplication : Application() {
    // Utilisation de 'lazy' pour que les objets soient initialisés lorsque nécessaire
    val database: RecipeDatabase by lazy {
        RecipeDatabase.getDatabase(this)
    }

    val recipeDao by lazy {
        database.recipeDao()
    }

    val recipeRepository by lazy {
        RecipeRepositoryImpl(RecipeService.api, recipeDao)
    }
}
