package com.a7medkenawy.foody.data.datasource.remote

import com.a7medkenawy.foody.data.network.FoodRecipesApi
import com.a7medkenawy.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi,
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(map: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(map)
    }
}