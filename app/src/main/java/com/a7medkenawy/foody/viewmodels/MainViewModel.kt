package com.a7medkenawy.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.a7medkenawy.foody.data.database.entities.FavoritesEntity
import com.a7medkenawy.foody.repo.Repository
import com.a7medkenawy.foody.data.database.entities.RecipesEntity
import com.a7medkenawy.foody.models.FoodRecipe
import com.a7medkenawy.foody.util.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {

    /** Room */
    val readRecipes: LiveData<List<RecipesEntity>> =
        repository.local.readFromDatabase().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> =
        repository.local.readAllFavoritesRecipe().asLiveData()

    fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipe(favoritesEntity)
        }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFavorites() = viewModelScope.launch(Dispatchers.IO) {
        repository.local.deleteAllFavorites()
    }

    /** Retrofit */
    val recipesResult: MutableLiveData<NetWorkResult<FoodRecipe>> = MutableLiveData()
    val searchRecipesResponse: MutableLiveData<NetWorkResult<FoodRecipe>> = MutableLiveData()

    fun setFoodRecipe(queries: Map<String, String>) = viewModelScope.launch {
        foodRecipeSafeCall(queries)
    }

    fun searchRecipes(queries: Map<String, String>) = viewModelScope.launch {
        searchRecipeSafeCall(queries)
    }

    private suspend fun searchRecipeSafeCall(queries: Map<String, String>) {
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                searchRecipesResponse.value = checkResponse(response)
            } catch (ex: Exception) {
                recipesResult.value = NetWorkResult.Error("Recipes Not Found.")
            }
        } else {
            recipesResult.value = NetWorkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun foodRecipeSafeCall(queries: Map<String, String>) {
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResult.value = checkResponse(response)

                val foodRecipe = recipesResult.value?.data
                if (foodRecipe != null) {
                    recipesResult.value?.data?.let {
                        insertRecipes(RecipesEntity(it))
                    }
//                    insertRecipes(RecipesEntity(recipesResult.value?.data!!))
                }
            } catch (ex: Exception) {
                recipesResult.value = NetWorkResult.Error("Recipes Not Found.")
            }
        } else {
            recipesResult.value = NetWorkResult.Error("No Internet Connection.")
        }
    }

    private fun checkResponse(response: Response<FoodRecipe>): NetWorkResult<FoodRecipe>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetWorkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetWorkResult.Error("API KEY Limited.")
            }
            response.body().toString().isNullOrEmpty() -> {
                NetWorkResult.Error("Food Recipes Not Found.")
            }
            response.isSuccessful -> {
                NetWorkResult.Success(response.body())
            }
            else -> {
                NetWorkResult.Error("Food Recipes Not Found.")
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork: Network = (connectivityManager.activeNetwork ?: false) as Network
        val capacities: NetworkCapabilities =
            (connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: false) as NetworkCapabilities
        return when {
            capacities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capacities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capacities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}