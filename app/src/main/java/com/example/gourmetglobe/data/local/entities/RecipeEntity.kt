package com.example.gourmetglobe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.gourmetglobe.data.model.Recipe



    @Entity(tableName = "recipes")
    data class RecipeEntity(
        @PrimaryKey
        val id: Int,                          // ID unique de la recette
        val title: String,                    // Titre de la recette
        val image: String?,                   // URL de l'image de la recette
        val summary: String?,                 // Résumé ou description courte
        val instructions: String?,            // Instructions de préparation
//        @Json(name = "readyInMinutes")
        val readyInMinutes: Int?,             // Temps de préparation
//        @Json(name = "servings")
        val servings: Int?,                   // Nombre de portions
        val calories: Int?,                   // Nombre de calories
        val cuisine: String? = null, // Types de cuisines (ex. : "Italian", "French")
        val dishTypes: List<String> = emptyList(), // Types de plat (ex. : "dessert", "main course")
        val intolerances: List<String> = emptyList(), // Liste des allergènes
        val diets: List<String> = emptyList(),       // Régimes alimentaires (ex. : "vegan", "vegetarian")
        val ingredients: List<String> = emptyList(), // Liste des ingrédients nécessaires
        val equipment: List<String> = emptyList(),   // Liste des ustensiles nécessaires
        var isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite
    ) {

        fun RecipeEntity.toRecipe(): Recipe {
            return Recipe(
                id = this.id,
                title = this.title,
                image = this.image,
                description = this.summary,
                instructions = this.instructions,
                readyInMinutes = this.readyInMinutes,
                servings = this.servings,
                calories = this.calories,
                cuisine = this.cuisine,
                dishTypes = this.dishTypes,
                intolerances = this.intolerances,
                diets = this.diets,
                ingredients = this.ingredients,
                equipment = this.equipment,
                isFavorite = this.isFavorite
            )
        }
    }