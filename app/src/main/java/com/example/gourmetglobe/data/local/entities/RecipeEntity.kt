package com.example.gourmetglobe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gourmetglobe.data.model.Recipe

/**
 * Représente une recette dans la base de données locale.
 * Cette classe est utilisée pour stocker les informations d'une recette sous forme d'entité Room.
 *
 * Chaque instance de cette classe correspond à une ligne de la table "recipes" dans la base de données.
 * Elle contient des informations sur la recette (voir ci-dessous).
 *
 * @property id L'ID unique de la recette.
 * @property title Le titre de la recette.
 * @property image L'URL de l'image associée à la recette.
 * @property summary Un résumé ou une description courte de la recette.
 * @property instructions Les instructions de préparation de la recette.
 * @property readyInMinutes Le temps de préparation en minutes.
 * @property servings Le nombre de portions de la recette.
 * @property calories Le nombre de calories par portion.
 * @property cuisine Le type de cuisine auquel la recette appartient (ex. : "Italian", "French").
 * @property dishTypes Les types de plat (ex. : "dessert", "main course").
 * @property intolerances La liste des allergènes associés à la recette.
 * @property diets La liste des régimes alimentaires compatibles avec la recette (ex. : "vegan", "vegetarian").
 * @property ingredients La liste des ingrédients nécessaires pour préparer la recette.
 * @property equipment La liste des ustensiles nécessaires pour la recette.
 * @property isFavorite Un indicateur qui indique si la recette est marquée comme favorite.
 */

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

    /**
     * Fusionne l'état des favoris d'une recette locale.
     * @param localRecipe La recette locale à fusionner avec l'instance actuelle.
     * @return l'instance mise à jour (La valeur isFavorite à était  mise à jour) pour la recette.
     */
        fun mergeFavorites(localRecipe: RecipeEntity): RecipeEntity {
            this.isFavorite = localRecipe.isFavorite
            return this
        }
    }
