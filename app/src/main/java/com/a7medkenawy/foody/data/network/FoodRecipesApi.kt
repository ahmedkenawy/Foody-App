package com.a7medkenawy.foody.data.network

import com.a7medkenawy.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap map: Map<String, String>): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap map: Map<String, String>): Response<FoodRecipe>
}