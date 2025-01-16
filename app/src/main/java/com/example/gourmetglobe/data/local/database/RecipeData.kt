package com.example.gourmetglobe.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gourmetglobe.data.local.data.RecipeDAO
import com.example.gourmetglobe.data.local.entities.Converters
import com.example.gourmetglobe.data.local.entities.RecipeEntity


/**
 * Base de données Room pour gérer les recettes. Cette classe définit la structure de la base de données
 * et fournit une instance unique pour l'accès aux entités `RecipeEntity` via le DAO `RecipeDAO`.
 * @see RecipeDAO Interface qui contient les méthodes d'accès aux données pour les recettes.
 */
@TypeConverters(Converters::class)
@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDAO

    companion object {
        // Instance unique pour la base de données
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        // Méthode utilitaire pour obtenir la base de données
        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                try {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_database"
                    )
                        // Ajoutez des logs pour voir si l'instance est construite avec succès
                        .build()
                    INSTANCE = instance
                    Log.d("test", "Database instance created successfully")
                    instance
                } catch (e: Exception) {
                    // Si une exception se produit lors de la création de la base de données
                    Log.e("test", "Error creating database: ${e.message}")
                    throw e
                }
            }

        }
    }
}


