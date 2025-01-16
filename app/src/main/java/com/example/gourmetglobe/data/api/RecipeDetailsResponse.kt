package com.example.gourmetglobe.data.api
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.squareup.moshi.JsonClass


/**
 * Représente la réponse provenant de l'API pour la requête relative aux détails d'un recette.
 * Cette classe contient toutes les informations disponibles pour une recette spécifique,
 * y compris son titre, ses instructions, les ingrédients, les allergènes, les types de plats,
 * etc.
 * @param id L'ID unique de la recette.
 * @param title Le titre de la recette.
 * @param image L'URL de l'image représentant la recette (peut être `null`).
 * @param summary Une description courte de la recette (peut être `null`).
 * @param instructions Les instructions de préparation de la recette (peut être `null`).
 * @param readyInMinutes Le temps de préparation en minutes (peut être `null`).
 * @param servings Le nombre de portions que la recette peut servir (peut être `null`).
 * @param calories Le nombre de calories par portion (peut être `null`).
 * @param cuisine Le type de cuisine de la recette (peut être `null`).
 * @param dishTypes La liste des types de plats (par exemple, "entrée", "dessert", "plat principal").
 * @param intolerances Liste des allergènes ou intolérances présentes dans la recette.
 * @param diets Liste des régimes alimentaires pour lesquels la recette est adaptée.
 * @param extendedIngredients La liste des ingrédients nécessaires pour réaliser la recette.
 * @param equipment Liste des équipements nécessaires pour la préparation de la recette.
 */
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

    data class Ingredient(
        val name: String,
        val amount: String,
        val unit: String
    )


    /**
     * Convertit un objet `RecipeDetailsResponse` en une entité `RecipeEntity` utilisable dans Room.
     * Cette fonction est utilisée pour enregistrer les informations de la recette dans la base de données locale.
     * @return Une instance de `RecipeEntity` correspondant à la recette.
     */
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
            equipment = equipment,
        )
    }
}