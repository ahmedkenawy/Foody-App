package com.a7medkenawy.foody.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.a7medkenawy.foody.models.FoodRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert
    suspend fun insertRecipes(foodRecipe: FoodRecipe)

    @Query("select * from recipes_table order by id asc")
    suspend fun readRecipes(): Flow<List<FoodRecipe>>
}