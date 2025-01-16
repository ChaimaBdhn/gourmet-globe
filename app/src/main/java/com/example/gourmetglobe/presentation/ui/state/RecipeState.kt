package com.example.gourmetglobe.presentation.ui.state

import com.example.gourmetglobe.data.local.entities.RecipeEntity


/**
 * "État scellé" représentant les différents états possibles des recettes.
 */
sealed class RecipeState {
    /**
     * État représentant le chargement des recette.
     */
    object Loading : RecipeState()

    /**
    * État représentant un succès dans le chargement des recettes.
    *
    * @property recipes la liste d'objets [RecipeEntity] contenant les receettes
    */
    data class Success(val recipes: List<RecipeEntity>) : RecipeState()

    /**
     * État représentant une erreur survenue lors du chargement des recettes.
     *
     * @property message Le message d'erreur décrivant le problème.
     */
    data class Error(val message: String) : RecipeState()
}