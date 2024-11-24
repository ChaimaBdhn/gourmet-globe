package com.example.gourmetglobe

import android.app.Application
import android.util.Log
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.database.RecipeDatabase
import com.example.gourmetglobe.data.model.RecipeService
import com.example.gourmetglobe.data.repository.RecipeRepositoryImpl

class MyApplication : Application() {
    // Déclarer les propriétés qui seront initialisées dans onCreate()
    lateinit var database: RecipeDatabase
    lateinit var recipeDao: RecipeDAO
    lateinit var recipeRepository: RecipeRepositoryImpl

    override fun onCreate() {
        Log.d("test", "Début Initiatlisation des var")

        super.onCreate()

        // Initialiser la base de données
        Log.d("test", "Initialisation de la base de données")
        database = RecipeDatabase.getDatabase(this)

        // Initialiser le DAO
        Log.d("test", "Initialisation du recipeDao")
        recipeDao = database.recipeDao()

        // Initialiser le repository
        Log.d("test", "Initialisation du recipeRepository")
        recipeRepository = RecipeRepositoryImpl(RecipeService.api, recipeDao)
    }
}
