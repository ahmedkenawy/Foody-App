package com.a7medkenawy.foody.util

class Constants {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "4eb37aeef2594750966f3ec47bc31e49"

        //API Query Map
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room
        const val RECIPES_DATABASE = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        //Bottom Sheet and Preferences
        const val DEFAULT_MEAL_TYPE = "main_course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"

        const val PREFERENCES_NAME="foody_preferences"
        const val SELECTED_MEAL_TYPE = "selectedMealType"
        const val SELECTED_MEAL_TYPE_ID = "selectedMealTypeId"
        const val SELECTED_DIET_TYPE = "selectedDietType"
        const val SELECTED_DIET_TYPE_ID = "selectedDieTypeId"


    }
}