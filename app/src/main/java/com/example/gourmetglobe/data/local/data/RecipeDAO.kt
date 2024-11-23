package com.example.gourmetglobe.data.local.data


import androidx.room.*
import androidx.room.OnConflictStrategy
import com.example.gourmetglobe.data.local.entities.RecipeEntity.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

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

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeByIdSync(id: Int): RecipeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%'")
    fun getRecipesByTitle(query: String): Flow <List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE cuisine LIKE '%' || :cuisine || '%'")
    fun getRecipesByCuisine(cuisine: String): Flow <List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<RecipeEntity>)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}