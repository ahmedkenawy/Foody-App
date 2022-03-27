package com.a7medkenawy.foody.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.a7medkenawy.foody.util.Constants.Companion.PREFERENCES_NAME
import com.a7medkenawy.foody.util.Constants.Companion.SELECTED_DIET_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.SELECTED_DIET_TYPE_ID
import com.a7medkenawy.foody.util.Constants.Companion.SELECTED_MEAL_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.SELECTED_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private object Preferences {
        val selectedMealType = preferencesKey<String>(SELECTED_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(SELECTED_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(SELECTED_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(SELECTED_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> =
        context.createDataStore(name = PREFERENCES_NAME)

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[Preferences.selectedMealType] = mealType
            preferences[Preferences.selectedMealTypeId] = mealTypeId
            preferences[Preferences.selectedDietType] = dietType
            preferences[Preferences.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndType: Flow<MealAndType> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { Preferences ->
        val selectedMealType =
            Preferences[DataStoreRepository.Preferences.selectedMealType] ?: "main_course"
        val selectedMealTypeId =
            Preferences[DataStoreRepository.Preferences.selectedMealTypeId] ?: 0
        val selectedDietType =
            Preferences[DataStoreRepository.Preferences.selectedDietType] ?: "gluten free"
        val selectedDietTypeId =
            Preferences[DataStoreRepository.Preferences.selectedDietTypeId] ?: 0
        MealAndType(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
    }

}

data class MealAndType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)