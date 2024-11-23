package com.example.gourmetglobe.data.model

import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,                          // ID unique de la recette
    val title: String,                    // Titre de la recette
    val image: String?,                   // URL de l'image de la recette
    val description: String?,             // Résumé ou description courte
    val instructions: String?,            // Instructions de préparation
    val readyInMinutes: Int?,             // Temps de préparation
    val servings: Int?,                   // Nombre de portions
    val calories: Int?,                   // Nombre de calories
    val cuisine: String? = null,          // Types de cuisines (ex. : "Italian", "French")
    val dishTypes: List<String> = emptyList(), // Types de plat (ex. : "dessert", "main course")
    val intolerances: List<String> = emptyList(), // Liste des allergènes
    val diets: List<String> = emptyList(),       // Régimes alimentaires (ex. : "vegan", "vegetarian")
    val ingredients: List<String> = emptyList(), // Liste des ingrédients nécessaires
    val equipment: List<String> = emptyList(),   // Liste des ustensiles nécessaires
    var isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite de base elle est false
)
 {
    // DEMANDER AU PROF SI C'EST UNE BONNE PRATIQUE !!!!! (pour ce projet)

     // Conversion de l'objet Recipe vers RecipeEntity pour la base de données
     fun toEntity(): RecipeEntity {
         return RecipeEntity(
             id = this.id,
             title = this.title,
             image = this.image,
             summary = this.description,  // Utilise la description comme résumé
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



// Représentation des informations nutritionnelles
@JsonClass(generateAdapter = true)
data class Nutrition(
    @Json(name = "nutrients") val nutrients: List<Nutrient>?
)

// Représentation d'un nutriment spécifique
@JsonClass(generateAdapter = true)
data class Nutrient(
    val name: String,  // Nom du nutriment, par exemple "Calories"
    val amount: Float, // Valeur du nutriment (ex: 25.025)
    val unit: String   // Unité de mesure, par exemple "kcal"
)