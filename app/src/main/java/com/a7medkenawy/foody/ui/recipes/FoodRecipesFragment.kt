package com.a7medkenawy.foody.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a7medkenawy.foody.R
import kotlinx.android.synthetic.main.fragment_food_recipies.view.*


class FoodRecipesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_food_recipies, container, false)

        view.shimmer_recycler_view.showShimmer()

        return view
    }


}