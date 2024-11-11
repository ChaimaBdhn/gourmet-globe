package com.example.gourmetglobe.data.api

import com.example.gourmetglobe.data.model.Recipe
import com.squareup.moshi.JsonClass

data class RecipeResponse(
    val results: List<Recipe>
)
