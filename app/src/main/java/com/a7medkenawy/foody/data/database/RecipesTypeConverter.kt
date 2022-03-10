package com.a7medkenawy.foody.data.database

import androidx.room.TypeConverter
import com.a7medkenawy.foody.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        var listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }
}