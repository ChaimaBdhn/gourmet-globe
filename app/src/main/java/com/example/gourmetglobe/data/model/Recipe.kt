package com.example.gourmetglobe.data.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val ingredients: List<Ingredients>, 

    @Json(name = "cookingMinutes") val cooking_time: Int,
    @Json(name = "preparationMinutes") val preparation_time: Int,

)
