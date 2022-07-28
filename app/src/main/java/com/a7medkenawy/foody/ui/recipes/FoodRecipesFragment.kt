package com.a7medkenawy.foody.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.foody.viewmodels.MainViewModel
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.adapter.RecipesAdapter
import com.a7medkenawy.foody.databinding.FragmentFoodRecipiesBinding
import com.a7medkenawy.foody.util.NetWorkListener
import com.a7medkenawy.foody.util.NetWorkResult
import com.a7medkenawy.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodRecipesFragment : Fragment() {

    private var _binding: FragmentFoodRecipiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var netWorkListener: NetWorkListener

    private val args by navArgs<FoodRecipesFragmentArgs>()

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
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFoodRecipiesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecyclerView()
        readFromDatabase()

        netWorkListener = NetWorkListener()
        lifecycleScope.launch {
            netWorkListener.checkNetworkAvailability(requireActivity())
                .collect { statue ->
                    recipesViewModel!!.netWorkStatue=statue
                    recipesViewModel!!.showNetworkStatue()
                }
        }

        binding.recipesFab.setOnClickListener {
            if (recipesViewModel!!.netWorkStatue)
                findNavController().navigate(R.id.action_foodRecipesFragment_to_recipesBottomSheet)
            else
                recipesViewModel!!.showNetworkStatue()
        }

        return binding.root
    }

    private fun readFromDatabase() {
        lifecycleScope.launch {
            mainViewModel!!.readRecipes.observe(viewLifecycleOwner) {
                if (it.isNotEmpty() && !args.backFromBottomSheet) {
                    mAdapter!!.setData(it[0].foodRecipe)
                    hideShimmerRV()
                } else {
                    getResponseFromApi()
                }
            }
        }
    }

    private fun loadFromDatabase() {
        lifecycleScope.launch {
            mainViewModel!!.readRecipes.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mAdapter!!.setData(it[0].foodRecipe)
                    hideShimmerRV()
                }
            }
        }
    }

    private fun getResponseFromApi() {
        mainViewModel!!.setFoodRecipe(recipesViewModel!!.applyQueries())
        mainViewModel!!.recipesResult.observe(viewLifecycleOwner) { response ->
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
        }
    }


    private fun setUpRecyclerView() {
        binding.shimmerRecyclerView.adapter = mAdapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.shimmerRecyclerView.showShimmer()
    }


    private fun showShimmerRV() {
        binding.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerRV() {
        binding.shimmerRecyclerView.hideShimmer()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}