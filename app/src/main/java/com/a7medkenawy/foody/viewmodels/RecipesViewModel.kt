package com.a7medkenawy.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.a7medkenawy.foody.data.DataStoreRepository
import com.a7medkenawy.foody.util.Constants
import com.a7medkenawy.foody.util.Constants.Companion.API_KEY
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var mealType = DEFAULT_MEAL_TYPE
    var dietType = DEFAULT_DIET_TYPE
    val readMealAndType = dataStoreRepository.readMealAndType

    fun saveMealAndType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(): Map<String, String> {
        val queries = HashMap<String, String>()

        viewModelScope.launch {
            readMealAndType.collect { value ->
                mealType = value.selectedMealType
                mealType = value.selectedMealType
            }
        }

        queries[Constants.QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = API_KEY
        queries[Constants.QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[Constants.QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}