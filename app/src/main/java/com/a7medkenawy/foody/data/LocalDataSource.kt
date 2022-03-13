package com.a7medkenawy.foody.data

import com.a7medkenawy.foody.data.database.RecipesDatabase
import com.a7medkenawy.foody.data.database.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val database: RecipesDatabase
){
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        database.recipeDao().insertRecipes(recipesEntity)
    }

    fun readFromDatabase()=database.recipeDao().readRecipes()

}