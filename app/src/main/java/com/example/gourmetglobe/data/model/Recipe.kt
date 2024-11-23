package com.example.gourmetglobe.data.model

import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,
    val title: String,
    val image: String?,
    val description: String?,
    val instructions: String?,
    val calories: Int?, // Info sur les calories
    val diets: List<String>, //  Diètes applicable (ex: "vegetarian", "vegan")
    val intolerances: List<String>, // List des intolérances (ex: "gluten", "soja")
    val dishTypes: List<String>, // Type de plat (ex: "dinner", "dessert")
    val equipment: List<String>, // ustensiles nécessaires
    val cuisine: String?, // Type de cuisine (ex  : Italian)
    val nutrition: Nutrition?, // Données nutritionnelles
    val isFavorite: Boolean = false // Favorie   (false par défaut)
)
 {
    // DEMANDER AU PROF SI C'EST UNE BONNE PRATIQUE !!!!! (pour ce projet)

    fun toEntity(): RecipeEntity {
        return RecipeEntity(
            id = this.id,
            title = this.title,
            image = this.image,
            description = this.description,
            instructions = this.instructions,
            calories = this.calories,
            diets = this.diets.joinToString(","),
            intolerances = this.intolerances.joinToString(","),
            dishTypes = this.dishTypes.joinToString(","),
            equipment = this.equipment.joinToString(","),
            cuisine = this.cuisine,
            isFavorite = false
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