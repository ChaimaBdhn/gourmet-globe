package com.example.gourmetglobe.data.api

import com.example.gourmetglobe.data.model.Recipe
import com.squareup.moshi.JsonClass

/**
 * Représente la réponse d'une recherche de recettes provenant de l'API.
 * Cette classe contient une liste de recettes qui ont été récupérées en fonction des critères de recherche.
 * @param results La liste des recettes récupérées.
 */
@JsonClass(generateAdapter = true)
data class RecipeResponse(
    val results: List<Recipe>
)
