package com.a7medkenawy.foody.ui.fragments.detailsfragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.adapter.ingredientadapter.IngredientAdapter
import com.a7medkenawy.foody.databinding.FragmentIngredientsBinding
import com.a7medkenawy.foody.models.Result


class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        val args = arguments
        val myBundle: Result = args?.getParcelable("recipesBundle")!!
        val ingredientAdapter = IngredientAdapter()
        if (myBundle.extendedIngredients.isNullOrEmpty()) {
            binding.ingredientsRecyclerView.showShimmer()
        } else {
            binding.ingredientsRecyclerView.hideShimmer()
            ingredientAdapter.setData(myBundle.extendedIngredients!!)
            binding.ingredientsRecyclerView.adapter = ingredientAdapter
        }
    }
}