package com.example.gourmetglobe.data.model

import com.example.gourmetglobe.data.api.RecipeDetailsResponse.Ingredient
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,                          // ID unique de la recette
    val title: String,                    // Titre de la recette
    val image: String,                   // URL de l'image de la recette
    var isFavorite: Boolean = false             // Indique si la recette est marquée comme favorite de base elle est false
)
 {

     // Conversion de l'objet Recipe vers RecipeEntity pour la base de données
     fun toEntity(): RecipeEntity {
         return RecipeEntity(
             id = this.id,
             title = this.title,
             image = this.image,
             isFavorite = this.isFavorite
         )
     }
}