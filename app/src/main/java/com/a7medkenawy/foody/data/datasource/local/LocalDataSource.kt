package com.a7medkenawy.foody.data.datasource.local

import com.a7medkenawy.foody.data.database.RecipesDatabase
import com.a7medkenawy.foody.data.database.entities.FavoritesEntity
import com.a7medkenawy.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val database: RecipesDatabase,
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        database.recipeDao().insertRecipes(recipesEntity)
    }

    fun readFromDatabase() = database.recipeDao().readRecipes()

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        database.recipeDao().insertFavoriteRecipe(favoritesEntity)
    }

    fun readAllFavoritesRecipe(): Flow<List<FavoritesEntity>> {
        return database.recipeDao().readAllFavorites()
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        database.recipeDao().deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavorites() {
        database.recipeDao().readAllFavorites()
    }
}