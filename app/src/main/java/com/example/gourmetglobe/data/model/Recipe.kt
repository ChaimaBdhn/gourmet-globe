package com.example.gourmetglobe.data.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val isFavorite: Boolean

//    val vegan: Boolean? = null,
//    val vegetarian: Boolean? = null,
//
//    @Json(name = "cookingMinutes") val cookingTime: Int,
//    @Json(name = "preparationMinutes") val preparationTime: Int,

    )
