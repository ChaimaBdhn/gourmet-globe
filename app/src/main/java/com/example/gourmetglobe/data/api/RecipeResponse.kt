package com.example.gourmetglobe.data.api

import com.example.gourmetglobe.data.model.Recipe
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResponse(
    val results: List<Recipe>
)
