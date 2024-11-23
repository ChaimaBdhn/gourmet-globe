package com.example.gourmetglobe.data.local.entities

import androidx.room.Entity

class RecipeEntity {

    @Entity(tableName = "recipes")
    data class RecipeEntity(
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
        val isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite
    )


//    fun toDomain(): Recipe {
//        return Recipe(
//            id = this.id,
//            title = this.title,
//            image = this.image,
//            description = this.description,
//            instructions = this.instructions,
//            calories = this.calories,
//            diets = this.diets.split(",").filter { it.isNotBlank() },
//            intolerances = this.intolerances.split(",").filter { it.isNotBlank() },
//            dishTypes = this.dishTypes.split(",").filter { it.isNotBlank() },
//            equipment = this.equipment.split(",").filter { it.isNotBlank() },
//            cuisine = this.cuisine
//        )
//    }

}