package com.example.gourmetglobe.data.api
import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeDetailsResponse(
    val id: Int,                        // ID unique de la recette
    val title: String,                  // Titre de la recette
    val image: String?,                 // URL de l'image de la recette
    val summary: String?,               // Résumé ou description courte
    val instructions: String?,          // Instructions de préparation
    val readyInMinutes: Int?,           // Temps de préparation
    val servings: Int?,                 // Nombre de portions
    val calories: Int?,                 // Nombre de calories
    val cuisine: String?,               // Types de cuisines
    val dishTypes: List<String> = emptyList(),    // Types de plat (ex. : "dessert", "main course")
    val intolerances: List<String> = emptyList(), // Liste des allergènes
    val diets: List<String> = emptyList(),        // Régimes alimentaires
    val extendedIngredients: List<Ingredient> = emptyList(), // Liste des ingrédients nécessaires
    val equipment: List<String> = emptyList()  // Liste des ustensiles nécessaires
) {
    data class Nutrition(
        val nutrients: List<Nutrient>
    ) {
        data class Nutrient(
            val name: String,
            val amount: Float,
            val unit: String
        )
    }

    data class Ingredient(
        val name: String,
        val amount: String,
        val unit: String
    )

    // Fonction pour convertir la réponse API en une entité Room
    fun toEntity(): RecipeEntity {
        return RecipeEntity(
            id = id,
            title = title,
            image = image,
            summary = summary,
            instructions = instructions,
            readyInMinutes = readyInMinutes,
            servings = servings,
            calories = calories,
            cuisine = cuisine,
            dishTypes = dishTypes,
            intolerances = intolerances,
            diets = diets,
            ingredients = extendedIngredients.map { it.name }, // Extraction des noms des ingrédients
            equipment = equipment,          // Extraction des noms des ustensiles
            isFavorite = false  // Par défaut, les recettes ne sont pas favorites
        )
    }
}