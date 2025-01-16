package com.example.gourmetglobe.presentation.ui.state

import com.example.gourmetglobe.data.local.entities.RecipeEntity


/**
 * "État scellé" représentant les différents états possibles pour les détails d'une recette.
 */
sealed class RecipeDetailsState {


    /**
     * État représentant le chargement des détails d'une recette.
     */
    object Loading : RecipeDetailsState()


    /**
     * État représentant un succès dans le chargement des détails d'une recette.
     *
     * @property recipe L'objet [RecipeEntity] contenant les détails de la recette.
     */
    data class Success(val recipe: RecipeEntity) : RecipeDetailsState()



    /**
     * État représentant une erreur survenue lors du chargement des détails d'une recette.
     *
     * @property message Le message d'erreur décrivant le problème.
     */
    data class Error(val message: String) : RecipeDetailsState()
}