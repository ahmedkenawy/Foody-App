package com.a7medkenawy.foody.data.database

import androidx.room.*
import com.a7medkenawy.foody.data.database.entities.FavoritesEntity
import com.a7medkenawy.foody.data.database.entities.RecipesEntity
import com.a7medkenawy.foody.models.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("select * from recipes_table order by id asc")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("select * from favorites_table order by id asc")
    fun readAllFavorites(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("delete  from favorites_table")
    suspend fun deleteAllFavorites()
}