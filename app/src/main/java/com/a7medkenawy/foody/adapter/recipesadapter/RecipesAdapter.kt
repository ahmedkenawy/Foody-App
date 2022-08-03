package com.a7medkenawy.foody.adapter.recipesadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.foody.databinding.RecipiesRowLayoutBinding
import com.a7medkenawy.foody.models.FoodRecipe
import com.a7medkenawy.foody.models.Result
import com.a7medkenawy.foody.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {
    var recipes = emptyList<Result>()

    fun setData(newData: FoodRecipe) {
        var recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        var diffResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            RecipiesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        var currentRecipe = recipes[position]
        holder.binding.result = currentRecipe
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = recipes.size

    class RecipeViewHolder(var binding: RecipiesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}