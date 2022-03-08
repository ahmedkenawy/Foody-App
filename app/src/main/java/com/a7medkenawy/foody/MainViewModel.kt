package com.a7medkenawy.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a7medkenawy.foody.data.Repository
import com.a7medkenawy.foody.models.FoodRecipe
import com.a7medkenawy.foody.util.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    val recipesResult: MutableLiveData<NetWorkResult<FoodRecipe>> = MutableLiveData()

    fun setFoodRecipe(queries: Map<String, String>) = viewModelScope.launch {
        foodRecipeSafeCall(queries)
    }

    private suspend fun foodRecipeSafeCall(queries: Map<String, String>) {
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResult.value = checkResponse(response)
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