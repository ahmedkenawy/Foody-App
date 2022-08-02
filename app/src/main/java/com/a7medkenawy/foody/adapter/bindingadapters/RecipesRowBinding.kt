package com.a7medkenawy.foody.adapter.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.models.Result
import com.a7medkenawy.foody.ui.recipes.FoodRecipesFragmentDirections
import org.jsoup.Jsoup

class RecipesRowBinding {

    companion object {

        @BindingAdapter("sendDataToDetailsActivity")
        @JvmStatic
        fun sendDataToDetailsActivity(constraintLayout: ConstraintLayout, result: Result) {
            constraintLayout.setOnClickListener {
                try {

                    val action =
                        FoodRecipesFragmentDirections.actionFoodRecipesFragmentToDetailsActivity(
                            result)
                    constraintLayout.findNavController().navigate(action)
                } catch (ex: Exception) {
                    Log.d("ERROR", ex.message.toString())

                }
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

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String) {
            val desc = Jsoup.parse(description).text()
            textView.text = desc

        }
    }
}