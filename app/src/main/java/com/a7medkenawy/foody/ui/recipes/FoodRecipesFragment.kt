package com.a7medkenawy.foody.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.foody.viewmodels.MainViewModel
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.adapter.RecipesAdapter
import com.a7medkenawy.foody.util.Constants
import com.a7medkenawy.foody.util.Constants.Companion.API_KEY
import com.a7medkenawy.foody.util.NetWorkResult
import com.a7medkenawy.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_recipies.view.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodRecipesFragment : Fragment() {

    private lateinit var mView: View
    private var mAdapter: RecipesAdapter? = null
    private var mainViewModel: MainViewModel? = null
    private var recipesViewModel: RecipesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(this)[RecipesViewModel::class.java]
        mAdapter = RecipesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_food_recipies, container, false)

        setUpRecyclerView()
        readFromDatabase()

        return mView
    }

    private fun readFromDatabase() {
        lifecycleScope.launch {
            mainViewModel!!.readRecipes.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    mAdapter!!.setData(it[0].foodRecipe)
                    hideShimmerRV()
                } else {
                    getResponseFromApi()
                }
            })
        }
    }

    private fun loadFromDatabase() {
        lifecycleScope.launch {
            mainViewModel!!.readRecipes.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    mAdapter!!.setData(it[0].foodRecipe)
                    hideShimmerRV()
                }
            })
        }
    }

    private fun getResponseFromApi() {
        mainViewModel!!.setFoodRecipe(recipesViewModel!!.applyQueries())
        mainViewModel!!.recipesResult.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetWorkResult.Success -> {
                    response.data?.let {
                        mAdapter!!.setData(it)
                    }
                    hideShimmerRV()
                }
                is NetWorkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    loadFromDatabase()
                    hideShimmerRV()
                }
                is NetWorkResult.Loading -> {
                    showShimmerRV()
                }
            }
        })
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