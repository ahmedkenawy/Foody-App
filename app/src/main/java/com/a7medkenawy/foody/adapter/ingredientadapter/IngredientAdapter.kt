package com.a7medkenawy.foody.adapter.ingredientadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.foody.databinding.IngredientsRowLayoutBinding
import com.a7medkenawy.foody.models.ExtendedIngredient
import com.a7medkenawy.foody.util.RecipesDiffUtil

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var ingredients = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentSize = ingredients[position]
        holder.binding.ingredient = currentSize
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = ingredients.size

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientDiffUtil = RecipesDiffUtil(ingredients, newIngredients)
        val result = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredients = newIngredients
        result.dispatchUpdatesTo(this)
    }

    class IngredientViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}