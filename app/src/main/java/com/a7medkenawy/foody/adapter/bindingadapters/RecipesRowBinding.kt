package com.a7medkenawy.foody.adapter.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.models.Result
import com.a7medkenawy.foody.ui.recipes.FoodRecipesFragmentDirections

class RecipesRowBinding {

    companion object {

        @BindingAdapter("sendDataToDetailsActivity")
        @JvmStatic
        fun sendDataToDetailsActivity(constraintLayout: ConstraintLayout, result: Result) {
            constraintLayout.setOnClickListener {
                val action =
                    FoodRecipesFragmentDirections.actionFoodRecipesFragmentToDetailsActivity(result)
                constraintLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, ulr: String) {
            imageView.load(ulr) {
                crossfade(600)
                error(R.drawable.ic_error)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(view: TextView, likes: Int) {
            view.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(view: TextView, minutes: Int) {
            view.text = minutes.toString()
        }


        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun setVegans(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                }
            }
        }

    }
}