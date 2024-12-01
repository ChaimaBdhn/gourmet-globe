package com.example.gourmetglobe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gourmetglobe.data.model.Recipe



    @Entity(tableName = "recipes")
    data class RecipeEntity(
        @PrimaryKey
        val id: Int,                          // ID unique de la recette
        val title: String,                    // Titre de la recette
        val image: String?,                   // URL de l'image de la recette
        val summary: String?= null,                 // Résumé ou description courte
        val instructions: String?= null,            // Instructions de préparation
        val readyInMinutes: Int?= null,             // Temps de préparation
        val servings: Int?= null,                   // Nombre de portions
        val calories: Int?= null,                   // Nombre de calories
        val cuisine: String? = null, // Types de cuisines (ex. : "Italian", "French")
        val dishTypes: List<String> = emptyList(), // Types de plat (ex. : "dessert", "main course")
        val intolerances: List<String> = emptyList(), // Liste des allergènes
        val diets: List<String> = emptyList(),       // Régimes alimentaires (ex. : "vegan", "vegetarian")
        val ingredients: List<String> = emptyList(), // Liste des ingrédients nécessaires
        val equipment: List<String> = emptyList(),   // Liste des ustensiles nécessaires
        var isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite
    ) {
        fun mergeFavorites(localRecipe: RecipeEntity): RecipeEntity {
            this.isFavorite = localRecipe.isFavorite
            return this
        }
    }
