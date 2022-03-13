package com.a7medkenawy.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.a7medkenawy.foody.util.Constants

class RecipesViewModel(application: Application) : AndroidViewModel(application) {
    fun applyQueries(): Map<String, String> {
        val queries = HashMap<String, String>()

        queries[Constants.QUERY_NUMBER] = "50"
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = "snack"
        queries[Constants.QUERY_DIET] = "vegan"
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}