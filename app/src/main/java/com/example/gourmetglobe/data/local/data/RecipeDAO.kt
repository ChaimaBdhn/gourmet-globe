package com.example.gourmetglobe.data.local.data


import androidx.room.*
import androidx.room.OnConflictStrategy
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow


/**
 * Le (DAO) nous permet d'interagir avec la base de données Room concernant les recettes.
 * Cette interface contient des méthodes pour effectuer les opérations de création, d'insertion, de suppression, ...
 * sur les entités `RecipeEntity`.
 *
 * Les méthodes utilisent les flow pour récupérer les données de manière asynchrone.
 */

@Dao
interface RecipeDAO {

    /**
     * Recupére toute les recettes présentes dans la base
     * @return un flux contenant la liste de toutes les recettes
     */
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>


    /**
     * Recupére toute les recettes qui sont comme favorites dans la base
     * @return un flux contenant la liste de toutes les recettes favorites
     */
    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    /**
     * Recherche des recettes en fonction de filtres multiples. Chaque filtre est optionnel et peut être nul.
     * @return Un flux contenant la liste des recettes qui correspondent aux filtres appliqués.
     */
    @Query("""
        SELECT * FROM recipes
        WHERE (:title IS NULL OR title LIKE '%' || :title || '%')
        AND (:cuisine IS NULL OR cuisine = :cuisine)
        AND (:diet IS NULL OR diets LIKE '%' || :diet || '%')
        AND (:dishType IS NULL OR dishTypes = :dishType)
        AND (:intolerances IS NULL OR intolerances LIKE '%' || :intolerances || '%')
        AND (:equipment IS NULL OR equipment LIKE '%' || :equipment || '%')
        AND (:ingredients IS NULL OR ingredients LIKE '%' || :ingredients || '%')
        AND (:minCalories IS NULL OR calories >= :minCalories)
        AND (:maxCalories IS NULL OR calories <= :maxCalories)
        AND (:maxReadyTime IS NULL OR readyInMinutes <= :maxReadyTime)
    """)
    fun getRecipesByFilters(
        title: String? = null,
        cuisine: String? = null,
        diet: String? = null,
        dishType: String? = null,
        intolerances: String? = null,
        equipment: String? = null,
        ingredients: String? = null,
        minCalories: Int? = null,
        maxCalories: Int? = null,
        maxReadyTime: Int? = null
    ): Flow<List<RecipeEntity>>


    /**
     * Récupère une recette spécifique par son ID.
     *
     * @param id L'identifiant de la recette à récupérer.
     * @return L'entité `RecipeEntity` correspondant à la recette spécifiée.
     */
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeByIdSync(id: Int): RecipeEntity


    /**
     * Insère une recette dans la base de données. Si la recette existe déjà, elle sera remplacée.
     *
     * @param recipe L'entité `RecipeEntity` à insérer.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)


    /**
     * Insère plusieurs recettes dans la base de données. Les recettes existantes seront remplacées.
     *
     * @param recipes La liste d'entités `RecipeEntity` à insérer.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)


    /**
     * Met à jour une recette dans la base de données.
     *
     * @param recipe L'entité `RecipeEntity` à mettre à jour.
     */
    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

}
