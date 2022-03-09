package com.a7medkenawy.foody.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.foody.viewmodels.MainViewModel
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.adapter.RecipesAdapter
import com.a7medkenawy.foody.util.Constants
import com.a7medkenawy.foody.util.Constants.Companion.API_KEY
import com.a7medkenawy.foody.util.NetWorkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_recipies.view.*


@AndroidEntryPoint
class FoodRecipesFragment : Fragment() {

    private lateinit var mView: View
    private val mAdapter by lazy { RecipesAdapter() }
    private val mainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView=inflater.inflate(R.layout.fragment_food_recipies, container, false)

        setUpRecyclerView()
        getResponseFromApi()

        return mView
    }

    fun getResponseFromApi() {
        mainViewModel.setFoodRecipe(applyQueries())
        mainViewModel.recipesResult.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetWorkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it.results)
                    }
                    hideShimmerRV()
                }
                is NetWorkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    hideShimmerRV()
                }
                is NetWorkResult.Loading -> {
                    showShimmerRV()
                }
            }
        })
    }

    private fun applyQueries(): Map<String, String> {
        val queries = HashMap<String, String>()

        queries[Constants.QUERY_NUMBER] = "50"
        queries[Constants.QUERY_API_KEY] = API_KEY
        queries[Constants.QUERY_TYPE] = "snack"
        queries[Constants.QUERY_DIET] = "vegan"
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    private fun setUpRecyclerView() {
        mView.shimmer_recycler_view.adapter = mAdapter
        mView.shimmer_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        mView.shimmer_recycler_view.showShimmer()
    }


    private fun showShimmerRV() {
        mView.shimmer_recycler_view.showShimmer()
    }

    private fun hideShimmerRV() {
        mView.shimmer_recycler_view.hideShimmer()
    }


}