package com.example.gourmetglobe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

class RecipeEntity {

    @Entity(tableName = "recipes")
    data class RecipeEntity(
        @PrimaryKey val id: Int,
        val title: String,
        val image: String,
        val isFavorite: Boolean // Pour gérer les favoris
    )


}