package com.example.gourmetglobe.data.model
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.squareup.moshi.JsonClass

/**
 * Représente une recette pour la requête API.
 * Cette classe est utilisée pour manipuler les informations d'une recette, telles que son ID, son titre, son image,
 * tels que réçu via l'api.
 *On initalise isFavorite à false car lorsque l'on récupere les recettes via l'api, cette donnée n'existe pas.
 * Nous lui donnons un sens grâce a RecipeEntity et à la logique métier de notre application.
 *
 * @property id L'ID unique de la recette.
 * @property title Le titre de la recette.
 * @property image L'URL de l'image associée à la recette.
 * @property isFavorite Boolean pour dire si la recette est en favorite ou non. Par défaut, c'est false.
 */

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,                          // ID unique de la recette
    val title: String,                    // Titre de la recette
    val image: String,                   // URL de l'image de la recette
    var isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite de base elle est false
)
 {
     /**
      * Convertit l'objet `Recipe` en une entité `RecipeEntity` utilisable dans la base de données locale.
      * Cette fonction est utile pour persister les données d'une recette sous forme d'entité Room dans la base de données.
      *
      * @return Une instance de `RecipeEntity` représentant la recette.
      */
     fun toEntity(): RecipeEntity {
         return RecipeEntity(
             id = this.id,
             title = this.title,
             image = this.image,
             isFavorite = this.isFavorite
         )
     }
}