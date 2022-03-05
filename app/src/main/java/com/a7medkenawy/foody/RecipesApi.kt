package com.a7medkenawy.foody

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap map: Map<String,String>)
}