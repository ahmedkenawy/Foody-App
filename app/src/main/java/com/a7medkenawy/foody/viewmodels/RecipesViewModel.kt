package com.a7medkenawy.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.a7medkenawy.foody.repo.DataStoreRepository
import com.a7medkenawy.foody.util.Constants
import com.a7medkenawy.foody.util.Constants.Companion.API_KEY
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository,
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var netWorkStatue = false


    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int,
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    val readMealTypeAndDiet = dataStoreRepository.readMealTypeAndDiet


    fun applyQueries(): Map<String, String> {
        val queries = HashMap<String, String>()
        viewModelScope.launch {
            readMealTypeAndDiet.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }
        queries[Constants.QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatue() {
        if (!netWorkStatue) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_LONG).show()
        }
    }

    fun applySearchQueries(searchQuery: String): Map<String, String> {
        val queries = HashMap<String, String>()
        queries[Constants.QUERY_KEYWORD] = searchQuery
        queries[Constants.QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}