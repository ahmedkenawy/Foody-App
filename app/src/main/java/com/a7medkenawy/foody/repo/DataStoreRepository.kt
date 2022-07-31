package com.a7medkenawy.foody.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.a7medkenawy.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
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
class DataStoreRepository @Inject constructor(@ApplicationContext val context: Context) {

    private object PreferencesKey {
        val selectedMealType = stringPreferencesKey(SELECTED_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(SELECTED_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(SELECTED_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(SELECTED_DIET_TYPE_ID)
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int,
    ) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.selectedMealType] = mealType
            preferences[PreferencesKey.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKey.selectedDietType] = dietType
            preferences[PreferencesKey.selectedDietTypeId] = dietTypeId

        }
    }

    val readMealTypeAndDiet: Flow<MealTypeAndDiet> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedMealType = preferences[PreferencesKey.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferencesKey.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferencesKey.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferencesKey.selectedDietTypeId] ?: 0

            MealTypeAndDiet(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    data class MealTypeAndDiet(
        val selectedMealType: String,
        val selectedMealTypeId: Int,
        val selectedDietType: String,
        val selectedDietTypeId: Int,
    )
}